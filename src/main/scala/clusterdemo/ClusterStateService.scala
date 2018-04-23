package clusterdemo

import akka.actor.{ActorRef, ActorSystem}
import akka.cluster.Cluster
import akka.pattern.{AskTimeoutException, ask}
import akka.util.Timeout

import scala.concurrent.Future
import scala.concurrent.duration._

class ClusterStateService(system: ActorSystem, singleton: ActorRef) {
  import system.dispatcher
  private val cluster = Cluster(system)
  private implicit val timeout: Timeout = Timeout(1.seconds)

  def clusterState: Future[ClusterState] = {
    (singleton ? SingletonActor.GetAddress)
      .mapTo[SingletonActor.SingletonAddress]
      .map { address =>
        ClusterState(cluster.state, Some(address.address))
      }
      .recover {
        case ex: AskTimeoutException =>
          ClusterState(cluster.state, None)
      }
  }
}
