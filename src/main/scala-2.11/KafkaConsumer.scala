/**
  * Created by abhishek.srivastava on 5/15/16.
  */

package com.abhi

import org.apache.kafka.clients.consumer._
import java.util.{Collections, Properties}
import collection.JavaConversions._
import org.apache.kafka.clients.producer.ProducerConfig

object KafkaConsumer {
  def ReadMessage : String = {
    var retVal : String = null
    var consumer : KafkaConsumer[String, String] = null
    try {
      val props = new Properties()
      props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.99.100:9092")
      props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer")
      props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer")
      consumer = new KafkaConsumer[String, String](props)
      println("going to subscribe to test1")
      consumer.subscribe(Collections.singletonList("test1"))
      println("successfully subscribed")
      while (true) {
        println("polling....")
        val records: ConsumerRecords[String, String] = consumer.poll(0) // batch
        for (
          record: ConsumerRecord[String, String] <- records
        ) {
          println("topic: %s key %s value %s partition %s offset %s", record.topic(),
            record.key(), record.value(), record.partition(), record.offset())
          retVal = record.value()
        }
      }
    }
    finally {
      if (consumer != null) consumer.close()
    }
    retVal
  }
}
