
//settings for all project
lazy val commonSettings = Seq(
  organization := "idv.myles.sample",

  version := "0.1.0",

  scalaVersion := "2.11.6",

  resolvers ++= Seq(
    "Twitter Maven" at "http://maven.twttr.com"
  )
)

//settings for root project
lazy val root = (project in file(".")).
  settings(commonSettings: _*).
  settings(net.virtualvoid.sbt.graph.Plugin.graphSettings: _*).
  settings(
    name := "web-sample",

    //assembly settings
    mainClass in assembly := Some("idv.myles.sample.HelloWorldServerMain"),

    libraryDependencies ++= Seq(
      //finatra
      "com.twitter.finatra" %% "finatra-http" % "2.0.0.M2",
      "com.twitter.finatra" %% "finatra-logback" % "2.0.0.M2",

      //logging
      "com.typesafe.scala-logging" %% "scala-logging-slf4j" % "2.1.2",

      //test
      "org.scalatest" %% "scalatest" % "2.2.4" % "test"
    )

  )

