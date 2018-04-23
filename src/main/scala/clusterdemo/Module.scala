package clusterdemo

import akka.NotUsed
import akka.actor.{ActorSystem, PoisonPill, Terminated}
import akka.cluster.singleton.{ClusterSingletonManager, ClusterSingletonManagerSettings, ClusterSingletonProxy, ClusterSingletonProxySettings}
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import com.typesafe.config.ConfigFactory

import scala.concurrent.{ExecutionContext, Future}

class Module extends HostName {
  private val config = ConfigFactory.parseString(s"akka.remote.netty.tcp.hostname = $hostname").withFallback(ConfigFactory.load())

  private implicit val system: ActorSystem = ActorSystem("ClusterDemo", config)
  private implicit val materializer: ActorMaterializer = ActorMaterializer()
  private implicit val executionContext: ExecutionContext = system.dispatcher

  private val singletonManager = system.actorOf(
    ClusterSingletonManager.props(
      singletonProps = SingletonActor.props(),
      terminationMessage = PoisonPill,
      settings = ClusterSingletonManagerSettings(system)),
    name = "singleton-manager")

  private val proxy = system.actorOf(
    ClusterSingletonProxy.props(
      singletonManagerPath = "/user/singleton-manager",
      settings = ClusterSingletonProxySettings(system)),
    name = "singleton")

  private val clusterStateService = new ClusterStateService(system, proxy)

  private val routing = new Routing(clusterStateService)

  def startup():Future[NotUsed] = {
    Http().bindAndHandle(routing.routes, hostname, config.getInt("akka.http.port")).map(_ => NotUsed)
  }

  def shutdown():Future[Terminated] = {
    system.terminate()
  }
}
