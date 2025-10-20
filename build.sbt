name := "shapes-oo-scala"
scalaVersion := "3.3.3"
version := "0.4"

libraryDependencies ++= Seq(
  "com.typesafe.scala-logging" %% "scala-logging" % "3.9.5",
  "org.slf4j" % "slf4j-simple" % "2.0.16" % Test,
  "org.creativescala" %% "doodle"     % "0.30.0",
  "org.scalatest"     %% "scalatest"  % "3.2.19"  % Test,
  "org.scalacheck"    %% "scalacheck" % "1.18.1"  % Test
)
