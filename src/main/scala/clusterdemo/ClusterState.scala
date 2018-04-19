package clusterdemo

import akka.actor.Address

case class ClusterState(
  leader: Option[Address] = None,
  members: List[Address] = List.empty,
  unreachableMembers: List[Address] = List.empty
) {
  def withLeader(leader: Option[Address]): ClusterState = copy(leader = leader)
  def withMember(member: Address): ClusterState = copy(members = members :+ member)
  def withoutMember(member: Address): ClusterState = copy(members = members.filterNot(_ == member))
  def withUnreachableMember(member: Address): ClusterState = copy(unreachableMembers = unreachableMembers :+ member)
  def withoutUnreachableMember(member: Address): ClusterState = copy(unreachableMembers = unreachableMembers.filterNot(_ == member))
}
