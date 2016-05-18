/**
  * Created by abhishek.srivastava on 5/15/16.
  */

package com.abhi

import java.util.{Collections, Properties}

import org.apache.kafka.clients.consumer._

import scala.collection.JavaConversions._

object KafkaConsumerAvro {
  def ReadMessage : Unit = {
    var consumer : KafkaConsumer[String, Object] = null
    try {
      val props = new Properties()
      props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092")
      props.put("zookeeper.server", "localhost:2181")
      props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer")
      props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "com.abhi.PersonDeserializer")
      props.put("group.id", "foo")
      props.put("enable.auto.commit", "false")
      consumer = new KafkaConsumer[String, Object](props)
      consumer.subscribe(Collections.singletonList("test1"))
      var flag = true
      while (flag) {
        val records: ConsumerRecords[String, Object] = consumer.poll(10) // batch
        for {
          record: ConsumerRecord[String, Object] <- records
        } {
          val person = record.value().asInstanceOf[Person]
          println(s"topic: ${record.topic()} key ${record.key()} first name: ${person.firstName} last name: ${person.lastName} partition ${record.partition()} offset ${record.offset()}")
          flag = if (person.firstName == "test 100") { false } else { true }
        }
        consumer.commitSync()
      }
    }
    finally {
      if (consumer != null) consumer.close()
    }
  }
}
