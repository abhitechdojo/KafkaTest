name := "KafkaTest"

version := "1.0"

scalaVersion := "2.11.8"

resolvers += "confluent repo" at "http://packages.confluent.io/maven/"

libraryDependencies ++= Seq(
  "org.apache.kafka" %% "kafka" % "0.9.0.1",
  "org.apache.avro" % "avro" % "1.8.0",
  "io.confluent" % "kafka-avro-serializer" % "1.0",
  "org.scalatest" % "scalatest_2.11" % "2.2.6" % "test"
)
