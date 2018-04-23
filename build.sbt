name := "akka-cluster"

scalaVersion := "2.12.5"

val akkaVersion = "2.5.12"
val akkaHttpVersion = "10.1.1"

fork := true

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor" % akkaVersion withSources(),
  "com.typesafe.akka" %% "akka-cluster" % akkaVersion withSources(),
  "com.typesafe.akka" %% "akka-cluster-sharding" % akkaVersion withSources(),
  "com.typesafe.akka" %% "akka-cluster-tools" % akkaVersion withSources(),
  "com.typesafe.akka" %% "akka-distributed-data" % akkaVersion withSources(),
  "com.typesafe.akka" %% "akka-testkit" % akkaVersion withSources(),
  "com.typesafe.akka" %% "akka-stream" % akkaVersion withSources(),
  "com.typesafe.akka" %% "akka-http" % akkaHttpVersion withSources(),
  "com.typesafe.akka" %% "akka-http-spray-json" % akkaHttpVersion withSources(),
  "com.lightbend.akka" %% "akka-split-brain-resolver" % "1.1.0",
  "org.scalatest" %% "scalatest" % "3.0.1" % "test"
)

enablePlugins(JavaAppPackaging)

addCommandAlias("run1", "runMain clusterdemo.Boot -Dakka.remote.netty.tcp.port=2551 -Dakka.http.port=8080")
addCommandAlias("run2", "runMain clusterdemo.Boot -Dakka.remote.netty.tcp.port=2552 -Dakka.http.port=8081")
addCommandAlias("run3", "runMain clusterdemo.Boot -Dakka.remote.netty.tcp.port=2553 -Dakka.http.port=8082")
addCommandAlias("run4", "runMain clusterdemo.Boot -Dakka.remote.netty.tcp.port=2554 -Dakka.http.port=8083")
addCommandAlias("run5", "runMain clusterdemo.Boot -Dakka.remote.netty.tcp.port=2555 -Dakka.http.port=8084")

