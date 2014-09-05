name := "software-companies-list"

version := "1.0-SNAPSHOT"

libraryDependencies ++= Seq(
  jdbc,
  anorm,
  cache,
   "org.webjars" %% "webjars-play" % "2.2.1-2",
   "org.webjars" % "bootstrap" % "3.1.1-1",
   "org.reactivemongo" %% "play2-reactivemongo" % "0.10.2"
)     

play.Project.playScalaSettings
