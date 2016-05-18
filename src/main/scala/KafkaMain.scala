/**
  * Created by abhishek.srivastava on 5/16/16.
  */

package com.abhi

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.{Await, Future}
import scala.concurrent.duration._

object KafkaMain {
  def main(args: Array[String]) : Unit = {

    val f1 = Future {
      KafkaConsumerString.ReadMessage
    }

    for (i <- 1 to 100) {
      val msg = "Hello World " + i
      KafkaProducerString.SendStringMessage(msg)
    }

    val f2 = Future {
      KafkaConsumerAvro.ReadMessage
    }

    for (i <- 1 to 100) {
      KafkaProducerAvro.SendAvroMessage("test " + i, "test " + i)
    }

    for {
      x <- f1
      y <- f2
    } yield (x, y)
  }
}
