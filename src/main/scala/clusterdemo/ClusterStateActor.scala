package clusterdemo

import akka.actor.{Actor, ActorLogging, Props}
import akka.cluster.Cluster
import akka.cluster.ClusterEvent._

object ClusterStateActor {
  case object GetClusterState

  def props(): Props = Props(new ClusterStateActor)
}

class ClusterStateActor extends Actor with ActorLogging {
  import ClusterStateActor._

  private val cluster = Cluster(context.system)
  private var clusterState = ClusterState()

  override def preStart(): Unit = {
    cluster.subscribe(self, initialStateMode = InitialStateAsEvents,
      classOf[MemberUp],
      classOf[MemberRemoved],
      classOf[ReachableMember],
      classOf[UnreachableMember],
      classOf[LeaderChanged])
    super.preStart()
  }

  override def postStop(): Unit = {
    cluster.unsubscribe(self)
    super.postStop()
  }

  override def receive: Receive = {
    case GetClusterState => sender() ! clusterState
    case MemberUp(member) =>
      log.info("UP: "+member.address)
      clusterState = clusterState.withMember(member.address)
    case MemberRemoved(member, _) =>
      log.info("DOWN: "+member.address)
      clusterState = clusterState.withoutMember(member.address).withoutUnreachableMember(member.address)
    case UnreachableMember(member) =>
      log.info("UNREACHABLE: "+member.address)
      clusterState = clusterState.withUnreachableMember(member.address)
    case ReachableMember(member) =>
      log.info("REACHABLE: "+member.address)
      clusterState = clusterState.withoutUnreachableMember(member.address)
    case LeaderChanged(leaderAddress) =>
      log.info("LEADER: "+leaderAddress)
      clusterState = clusterState.withLeader(leaderAddress)
  }
}
