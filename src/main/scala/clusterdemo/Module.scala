package clusterdemo

import java.net.{Inet4Address, InetAddress, NetworkInterface}

import akka.NotUsed
import akka.actor.{ActorSystem, Terminated}
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import com.typesafe.config.ConfigFactory

import scala.concurrent.{ExecutionContext, Future}
import scala.collection.convert.Wrappers.JEnumerationWrapper

trait HostName {
  protected val hostname: String = {
    Option(NetworkInterface.getByName("enp0s8")).flatMap { interface =>
      val addresses = JEnumerationWrapper(interface.getInetAddresses)

      addresses.find {
        case addr: Inet4Address => true
        case _ => false
      }
    }.map(_.getHostAddress).getOrElse("127.0.0.1")
  }
}

class Module extends HostName {
  private val config = ConfigFactory.parseString(s"akka.remote.netty.tcp.hostname = $hostname").withFallback(ConfigFactory.load())

  private implicit val system: ActorSystem = ActorSystem("ClusterDemo", config)
  private implicit val materializer: ActorMaterializer = ActorMaterializer()
  private implicit val executionContext: ExecutionContext = system.dispatcher

  private val clusterStateActor = system.actorOf(ClusterStateActor.props(), "cluster-state")

  private val routing = new Routing(clusterStateActor, shutdown)

  def startup():Future[NotUsed] = {
    Http().bindAndHandle(routing.routes, hostname, config.getInt("akka.http.port")).map(_ => NotUsed)
  }

  def shutdown():Future[Terminated] = {
    system.terminate()
  }
}
