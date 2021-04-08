version in Global := "0.1"

scalaVersion in Global := "2.13.5"

/*
  root project
  - aggregation
  change
 */

lazy val root = project.in(file("."))
  .settings(
    name := "lecture08",
    run := run.in(app, Compile, run).evaluated
  ).aggregate(library, app)

/*
  Library project
  - cross building
  - publishing
  - unit tests
  - license
 */
lazy val library = project.in(file("library"))
  .settings(
    crossScalaVersions += "2.12.12",
    libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.5" % Test,
    libraryDependencies += "org.augustjune" %% "canoe" % "0.5.1",
  )
/* app project
  - set main class
  - assembly jar
  - integration tests
  - source generation
  - release notes generation task
 */
lazy val app = project.in(file("app"))
  .dependsOn(library)
  .settings(
    Compile / run / mainClass := Some("org.myapp.MyMainApp"),
    mainClass in assembly := Some("org.myapp.MyMainApp")
  )