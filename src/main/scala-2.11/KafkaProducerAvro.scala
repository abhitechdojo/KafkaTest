/**
  * Created by abhishek.srivastava on 4/28/16.
  */
package com.abhi

import org.apache.kafka.clients.producer._
import org.apache.avro.generic.GenericData
import org.apache.avro.generic.GenericRecord
import org.apache.avro.Schema
import java.util.Properties

import scala.io.Source
import java.io._
import java.util.concurrent.TimeUnit._

import kafka.producer.KeyedMessage

object KafkaProducerAvro {

  def SendAvroMessage(schemaStr: String, firstName: String, lastName: String): Unit = {
    val inputRecord = createAvroRecord(schemaStr, firstName, lastName)
    val producer: KafkaProducer[String, Object] = CreateProducerAvro
    val producerAvroRecord = new ProducerRecord[String, Object]("test1", "key1", inputRecord)
    val rm = producer.send(producerAvroRecord).get
    println(s"offset: ${rm.offset()} partition: ${rm.partition()} topic: ${rm.topic()}")
    producer.close()
  }

  private def createAvroRecord(schemaStr: String, firstName: String, lastName : String): GenericRecord = {
    val parser = new Schema.Parser();
    val schema = parser.parse(schemaStr);
    val avroRecord = new GenericData.Record(schema);
    avroRecord.put("firstname", firstName);
    avroRecord.put("lastname", lastName);
    avroRecord
  }

  private def getAvroSchema: String = {
    val stream: InputStream = getClass.getResourceAsStream("/Person.avsc")
    val schemaStr = Source.fromInputStream(stream).getLines.mkString
    schemaStr
  }

  private def CreateProducerAvro: KafkaProducer[String, Object] = {
    val props = new Properties()
    props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092")
    props.put("schema.registry.url", "http://localhost:8081")
    props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer")
    props.put("value.serializer", "io.confluent.kafka.serializers.KafkaAvroSerializer")
    val producer = new KafkaProducer[String, Object](props)
    producer
  }
}
