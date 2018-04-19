package clusterdemo

import akka.actor.Address
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import spray.json.{DefaultJsonProtocol, JsString, JsValue, RootJsonFormat}

trait ClusterStateJsonProtocol extends SprayJsonSupport with DefaultJsonProtocol {
  implicit val addressFormat: RootJsonFormat[Address] = new RootJsonFormat[Address] {
    override def read(json: JsValue): Address = json match {
      case JsString(value) => throw new NotImplementedError("TODO")
      case other => throw new NotImplementedError("Expected an string but got: "+other)
    }

    override def write(obj: Address): JsValue = JsString(obj.toString)
  }
  implicit val clusterStateFormat: RootJsonFormat[ClusterState] = jsonFormat3(ClusterState)
}
