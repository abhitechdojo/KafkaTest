/**
  * Created by abhishek.srivastava on 5/16/16.
  */
package com.abhi

object KafkaTest {
  def main(args: Array[String]) : Unit = {
    val msg = "Hello World"
    println("going to send message")
    KafkaPublisher.SendStringMessage(msg)
    println("message sent successfully")
    println("going to subscribe")
    val value = KafkaConsumer.ReadMessage
    println("message subscribed successfully")
    println(value)
  }
}
