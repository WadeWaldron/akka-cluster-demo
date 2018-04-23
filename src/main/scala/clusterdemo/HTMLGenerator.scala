package clusterdemo

object HTMLGenerator {

  def generate(clusterState: ClusterState): String = {
    s"""<html>
       |<head>
       |  <meta http-equiv="refresh" content="1">
       |  <style type="text/css">
       |  .green {
       |    background-color: #BBFFBB;
       |  }
       |  .red {
       |    background-color: #FFBBBB;
       |  }
       |  .blue {
       |    background-color: #BBFFFF;
       |  }
       |  .yellow {
       |	background-color: #FFFFBB;
       |  }
       |</style>
       |</head>
       |  <body>
       |    ${generateState(clusterState).mkString("</br>\n")}
       |  </body>
       |</html>""".stripMargin
  }

  private def generateState(clusterState: ClusterState): Iterable[String] = {
    for(member <- clusterState.members.toList.sortBy(_._1)) yield {
      val address = member._1

      val role = if(clusterState.leader.contains(member._1))
        "<label class='green'> Leader</label>"
      else
        "<label class='blue'> Member</label>"

      val singleton = if(clusterState.singleton.contains(member._1))
        "<label class='yellow'> Singleton</label>"
      else
        ""

      val unreachable = if(member._2.unreachable)
        "<label class='red'> Unreachable</label>"
      else
        ""

      s"$address $role$singleton$unreachable"
    }
  }

}
