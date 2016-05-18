name := "KafkaTest"

version := "1.0"

scalaVersion := "2.10.4"

resolvers += "confluent repo" at "http://packages.confluent.io/maven/"

libraryDependencies ++= Seq(
  "org.apache.kafka" % "kafka_2.10" % "0.9.0.1",
  "org.apache.avro" % "avro" % "1.8.0",
  "com.gensler" % "scalavro_2.10" % "0.6.2",
  "org.scalatest" % "scalatest_2.10" % "2.2.6" % "test"
)

assemblyMergeStrategy in assembly := {
  case PathList(ps @ _*) if ps.last endsWith "StaticLoggerBinder.class" => MergeStrategy.first
  case PathList(ps @ _*) if ps.last endsWith "StaticMDCBinder.class" => MergeStrategy.first
  case PathList(ps @ _*) if ps.last endsWith "StaticMarkerBinder.class" => MergeStrategy.first
  case x =>
    val oldStrategy = (assemblyMergeStrategy in assembly).value
    oldStrategy(x)
}

runMain in (Compile) := Some("com.abhi.KafkaMain")