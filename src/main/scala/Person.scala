package com.abhi

import org.apache.kafka.common.serialization.{Deserializer, Serializer}
import com.gensler.scalavro.types.AvroType
import com.gensler.scalavro.io.AvroTypeIO
import java.io.{ByteArrayInputStream, ByteArrayOutputStream}
import scala.runtime.ObjectRef
import scala.util.{Failure, Success, Try}

case class Person (firstName: String, lastName: String)

class PersonDeserializer extends Deserializer[Person] {
  override def configure(configs: java.util.Map[String, _], isKey: Boolean) : Unit = {

  }

  override def deserialize(topic: String, input : Array[Byte]) : Person = {
    val inputStream = new ByteArrayInputStream(input)
    val personAvroType = AvroType[Person]
    personAvroType.io.read(inputStream) match {
      case Success(p) => p
      case Failure(error) => null
    }
  }

  override def close() : Unit = {}
}

class PersonSerializer extends Serializer[Person] {
  override def configure(configs: java.util.Map[String, _], isKey: Boolean) : Unit = {
  }

  override def serialize(topic: String, p : Person) : Array[Byte] = {
    val personAvroType = AvroType[Person]
    val byteArrayOS = new ByteArrayOutputStream()
    val x = personAvroType.io.write(p, byteArrayOS)
    byteArrayOS.toByteArray
  }

  override def close() : Unit = {

  }
}
