
name := "RecSystem"

version := "1.0"

scalaVersion := "2.11.11"

val sparkVersion = "2.4.4"
// Compiler settings. Use scalac -X for other options and their description.
// See Here for more info http://www.scala-lang.org/files/archive/nightly/docs/manual/html/scalac.html
scalacOptions ++= List("-feature","-deprecation", "-unchecked", "-Xlint")
assemblyOption in assembly := (assemblyOption in assembly).value.copy(includeScala = false)

mainClass in (Compile, run) := Some("com.RecSystem")

libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-core" % sparkVersion,
  "org.apache.spark" %% "spark-mllib" % sparkVersion,
  "org.apache.spark" %% "spark-sql" % sparkVersion,
  "org.apache.spark" % "spark-streaming_2.11" % sparkVersion,
  "org.apache.spark" % "spark-streaming-kafka-0-10_2.11"%sparkVersion,
  "com.github.fommil.netlib" % "all" % "1.1.2" pomOnly()
)

resolvers += "Sonatype Releases" at "https://oss.sonatype.org/content/repositories/releases/"
resolvers += "Sonatype Snapshots" at "http://oss.sonatype.org/content/repositories/snapshots"
fork in run := true
fork in test := true


assemblyMergeStrategy in assembly := {
  case PathList("META-INF", xs @ _*) => MergeStrategy.discard
  case x => MergeStrategy.first
}

lazy val intellijRunner = project.in(file("intellijRunner")).dependsOn(RootProject(file("."))).settings(
  scalaVersion := "2.11.11"
//  libraryDependencies ++= sparkDependencies.map(_ % "compile")
).disablePlugins(sbtassembly.AssemblyPlugin)


javaOptions in run ++= Seq(
  "-Dlog4j.debug=true",
  "-Dlog4j.configuration=log4j.properties")