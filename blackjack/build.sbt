lazy val root = (project in file("."))
  .settings(
    name := "Card Game GUI",
    scalaVersion := "2.11.8",
    version := "0.0.1",
    libraryDependencies ++= Seq (
    	"org.scalatest" %% "scalatest" % "3.0.1" % "test",
		"org.scala-lang" % "scala-swing" % "2.11+"
	)
  )