package clusterdemo

import akka.http.scaladsl.model.{ContentTypes, HttpEntity}
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import akka.util.Timeout

import scala.concurrent.duration._

class Routing(clusterStateService: ClusterStateService) {
  private implicit val timeout: Timeout = Timeout(5.seconds)

  val routes: Route = pathEndOrSingleSlash {
    get {
      onSuccess(clusterStateService.clusterState) { result =>
        complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, HTMLGenerator.generate(result)))
      }
    }
  }
}
