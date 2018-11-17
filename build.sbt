name := "Renty"

version := "0.1"

scalaVersion := "2.12.7"

resolvers += Resolver.jcenterRepo

val akkaHttp = "10.1.1"
val akka = "2.5.11"
val circe = "0.9.3"

libraryDependencies ++= Seq(
  "com.typesafe.sbt" % "sbt-native-packager" % "1.3.12"
  "com.typesafe.akka" %% "akka-http" % akkaHttp,
  "com.typesafe.akka" %% "akka-stream" % akka,
  "com.typesafe.akka" %% "akka-slf4j" % akka,

  "de.heikoseeberger" %% "akka-http-circe" % "1.20.1",

  "io.circe" %% "circe-generic" % circe,

  "ch.qos.logback" % "logback-classic" % "1.2.3",
  "org.mongodb.scala" %% "mongo-scala-driver" % "2.1.0",

  //test libraries
  "org.scalatest" %% "scalatest" % "3.0.1" % "test",
  "org.pegdown" % "pegdown" % "1.6.0" % "test",
  "com.typesafe.akka" %% "akka-http-testkit" % akkaHttp % "test",
  "org.mongodb" % "mongo-java-driver" % "3.4.2",
  "com.github.fakemongo" % "fongo" % "2.1.0" % "test"
)

testOptions in Test ++= Seq(
  Tests.Argument(TestFrameworks.ScalaTest, "-u", "target/test-reports"),
  Tests.Argument(TestFrameworks.ScalaTest, "-h", "target/test-reports")
)

enablePlugins(JavaServerAppPackaging)

val stage = taskKey[Unit]("Stage task")

val Stage = config("stage")

stage := {
  (packageWar in Compile).value
  (update in Stage).value.allFiles.foreach { f =>
    if (f.getName.matches("webapp-runner-[0-9\\.]+.jar")) {
      println("copying " + f.getName)
      IO.copyFile(f, baseDirectory.value / "target" / "webapp-runner.jar")
    }
  }
}

