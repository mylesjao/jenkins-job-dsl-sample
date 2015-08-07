//settings for all project
lazy val commonSettings = Seq(
  organization := "com.vpon.sample",

  version := "0.1.0",

  scalaVersion := "2.11.6"
)

//settings for root project
lazy val rootProject = (project in file(".")).
  settings(commonSettings: _*).
  settings(net.virtualvoid.sbt.graph.Plugin.graphSettings: _*).
  settings(
    name := "web-sample",

    //assembly settings
    //mainClass in assembly := Some("com.vpon.datapipeline.video.Main"),

    libraryDependencies ++= Seq(

      //util
      "com.typesafe" % "config" % "1.2.1",
      "com.github.nscala-time" %% "nscala-time" % "2.0.0",
      "com.google.guava" % "guava" % "18.0",
      "com.fasterxml.jackson.module" %% "jackson-module-scala" % "2.5.2",
      "com.github.fge" % "json-schema-validator" % "2.2.6",

      //logging
      "com.typesafe.scala-logging" %% "scala-logging-slf4j" % "2.1.2",

      //test
      "org.scalatest" %% "scalatest" % "2.2.4" % "test"
    )

  )

