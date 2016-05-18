/**
  * Created by abhishek.srivastava on 5/15/16.
  */

package com.abhi

import java.util.{Collections, Properties}

import org.apache.kafka.clients.consumer._

import scala.collection.JavaConversions._

object KafkaConsumerString {
  def ReadMessage : Unit = {
    var consumer : KafkaConsumer[String, String] = null
    try {
      val props = new Properties()
      props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092")
      props.put("zookeeper.server", "localhost:2181")
      props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer")
      props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer")
      props.put("group.id", "foo")
      consumer = new KafkaConsumer[String, String](props)
      consumer.subscribe(Collections.singletonList("test"))
      var flag = true
      while (flag) {
        val records: ConsumerRecords[String, String] = consumer.poll(10) // batch
        for (
          record: ConsumerRecord[String, String] <- records
        ) {
          println(s"topic: ${record.topic()} key ${record.key()} msg ${record.value()} partition ${record.partition()} offset ${record.offset()}")
          flag = if (record.value().toString.trim == "Hello World 100") { false } else { true }
        }
        consumer.commitSync()
      }
    }
    finally {
      if (consumer != null) consumer.close()
    }
  }
}
