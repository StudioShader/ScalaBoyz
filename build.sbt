version in Global := "0.1"

scalaVersion in Global := "2.13.5"

lazy val root = project.in(file("."))
  .settings(
    name := "telegram-bot",
    crossScalaVersions += "2.12.12",
    libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.5" % Test,
    libraryDependencies += "org.augustjune" %% "canoe" % "0.5.1",
    Compile / run / mainClass := Some("org.myapp.MyMainApp"),
    mainClass in assembly := Some("org.myapp.MyMainApp")
  )
.settings(
    semanticdbEnabled := true,
    semanticdbVersion := scalafixSemanticdb.revision,
    scalacOptions += "-Ywarn-unused"
  )
