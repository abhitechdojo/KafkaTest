/**
  * Created by abhishek.srivastava on 4/28/16.
  */
package com.abhi

import java.util.Properties
import java.util.concurrent.TimeUnit._

import org.apache.kafka.clients.producer._

object KafkaProducerString {

  def SendStringMessage(msg: String) : Unit = {
    val inputRecord = new ProducerRecord[String, String]("test", "key1", msg)
    val producer: KafkaProducer[String, String] = CreateProducerString
    val rm = producer.send(inputRecord).get(10, SECONDS)
    //println(s"offset: ${rm.offset()} partition: ${rm.partition()} topic: ${rm.topic()}")
    producer.close()
  }

  private def CreateProducerString: KafkaProducer[String, String] = {
    val props = new Properties()
    props.put("bootstrap.servers", "192.168.99.100:9092")
    props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer")
    props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer")
    props.put("batch.size", "0")
    props.put("client.id", "1")
    val producer = new KafkaProducer[String, String](props)
    producer
  }

}
