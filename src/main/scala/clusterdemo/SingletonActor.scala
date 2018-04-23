package clusterdemo

import akka.actor.{Actor, ActorLogging, Address, Props, Timers}
import akka.cluster.Cluster

object SingletonActor {
  case object GetAddress
  case class SingletonAddress(address: Address)

  def props(): Props = Props(new SingletonActor())
}

class SingletonActor extends Actor with ActorLogging with Timers {
  import SingletonActor._

  private val cluster = Cluster(context.system)
  private val clusterAddress =  cluster.selfAddress

  log.info("SINGLETON ONLINE: "+clusterAddress)

  override def receive: Receive = {
    case GetAddress =>
      sender() ! SingletonAddress(clusterAddress)
  }
}
