package clusterdemo

import akka.actor.{ActorRef, Terminated}
import akka.http.scaladsl.model.{ContentTypes, HttpEntity}
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import akka.pattern.ask
import akka.util.Timeout

import scala.concurrent.Future
import scala.concurrent.duration._

class Routing(clusterStateActor: ActorRef, shutdown: () => Future[Terminated]) extends ClusterStateJsonProtocol {
  private implicit val timeout = Timeout(5.seconds)

  val routes: Route =
    path("shutdown") {
      get {
        complete {
          shutdown()
          "Shutting Down"
        }
      }
    } ~ pathEndOrSingleSlash {
      get {
        onSuccess((clusterStateActor ? ClusterStateActor.GetClusterState).mapTo[ClusterState]) { result =>
          complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, HTMLGenerator.generate(result)))
        }
      }
    }
}
