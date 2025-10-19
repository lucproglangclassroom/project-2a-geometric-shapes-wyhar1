name := "shapes-oo-scala"
scalaVersion := "3.3.3"
version := "0.4"

libraryDependencies ++= Seq(
  "org.creativescala" %% "doodle"     % "0.30.0",
  "org.scalatest"     %% "scalatest"  % "3.2.19"  % Test,
  "org.scalacheck"    %% "scalacheck" % "1.18.1"  % Test
)
