/**
  * Created by abhishek.srivastava on 5/15/16.
  */

package com.abhi

import org.apache.kafka.clients.consumer._
import java.util.{Collections, Properties}
import collection.JavaConversions._
import org.apache.kafka.clients.producer.ProducerConfig

object KafkaConsumer {
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
        val records: ConsumerRecords[String, String] = consumer.poll(0) // batch
        for (
          record: ConsumerRecord[String, String] <- records
        ) {
          println(s"topic: %s key %s value %s partition %s offset %s", record.topic(), record.key(), record.value(), record.partition(), record.offset())
          flag = if (record.value() == "break") { false } else { true }
        }
      }
    }
    finally {
      if (consumer != null) consumer.close()
    }
  }
}
