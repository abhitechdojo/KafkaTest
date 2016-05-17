/**
  * Created by abhishek.srivastava on 5/16/16.
  */
package com.abhi
import scala.concurrent.{Future, Await}
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global

object KafkaMain {
  def main(args: Array[String]) : Unit = {
    val f = Future {
      println("going to subscribe")
      KafkaConsumer.ReadMessage
      println("message subscribed successfully")
    }

    for (i <- 1 to 100) {
      val msg = "Hello World " + i
      KafkaPublisher.SendStringMessage(msg)
    }
    KafkaPublisher.SendStringMessage("break")

    Await.result(f, 1000 second)
  }
}
