/**
  * Created by abhishek.srivastava on 4/28/16.
  */

package com.abhi

import java.io._
import java.util.Properties

import org.apache.kafka.clients.producer._

import scala.io.Source

object KafkaProducerAvro {

  def SendAvroMessage(firstName: String, lastName: String): Unit = {
    val producer: KafkaProducer[String, Object] = CreateProducerAvro
    val p = new Person(firstName, lastName)
    val producerAvroRecord = new ProducerRecord[String, Object]("test1", "key1", p.asInstanceOf[Object])
    val rm = producer.send(producerAvroRecord).get
    //println(s"offset: ${rm.offset()} partition: ${rm.partition()} topic: ${rm.topic()}")
    producer.close()
  }

  private def getAvroSchema: String = {
    val stream: InputStream = getClass.getResourceAsStream("/Person.avsc")
    val schemaStr = Source.fromInputStream(stream).getLines.mkString
    schemaStr
  }

  private def CreateProducerAvro: KafkaProducer[String, Object] = {
    val props = new Properties()
    props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092")
    //props.put("schema.registry.url", "http://localhost:8081")
    props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer")
    props.put("value.serializer", "com.abhi.PersonSerializer")
    val producer = new KafkaProducer[String, Object](props)
    producer
  }
}
