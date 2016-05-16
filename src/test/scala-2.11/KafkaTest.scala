/**
  * Created by abhishek.srivastava on 5/16/16.
  */

import org.scalatest._

class KafkaTest extends FunSuite {
  test("must be able to publish and subscribe") {
    val msg = "Hello World"
    println("going to send message")
    KafkaPublisher.SendStringMessage(msg)
    println("message sent successfully")
    println("going to subscribe")
    val value = KafkaConsumer.ReadMessage
    println("message subscribed successfully")
    println(value)
    assert(msg == value)
  }
}
