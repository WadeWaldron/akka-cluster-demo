package clusterdemo

import akka.actor.Address
import akka.cluster.ClusterEvent.CurrentClusterState

case class MemberState(unreachable: Boolean = false)

object ClusterState {
  def apply(currentState: CurrentClusterState, singleton: Option[Address]): ClusterState = {
    val leader = currentState.leader
    val members = currentState.members.map(member => member.address -> MemberState(currentState.unreachable.contains(member))).toMap
    ClusterState(leader = leader, singleton = singleton, members = members)
  }
}

case class ClusterState(leader: Option[Address] = None, singleton: Option[Address] = None, members: Map[Address, MemberState] = Map.empty) {

}
