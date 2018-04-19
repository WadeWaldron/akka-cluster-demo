package clusterdemo

object HTMLGenerator {

  def generate(clusterState: ClusterState): String = {
    s"""<html>
       |<head>
       |  <meta http-equiv="refresh" content="1">
       |</head>
       |  <body>
       |    ${generateState(clusterState).mkString("</br>")}
       |  </body>
       |</html>""".stripMargin
  }

  private def generateState(clusterState: ClusterState): List[String] = {
    for(member <- clusterState.members) yield {
      if(clusterState.leader.contains(member))
        s"Leader: $member"
      else if(clusterState.unreachableMembers.contains(member))
        s"Unreachable: $member"
      else
        s"Member: $member"
    }
  }

}
