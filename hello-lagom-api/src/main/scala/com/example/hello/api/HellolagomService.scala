package com.example.hello.api

import akka.{Done, NotUsed}

import scala.collection.mutable.ListBuffer
//import akka.NotUsed
import com.lightbend.lagom.scaladsl.api.broker.Topic
import com.lightbend.lagom.scaladsl.api.broker.kafka.{KafkaProperties, PartitionKeyStrategy}
import com.lightbend.lagom.scaladsl.api.transport.Method
import com.lightbend.lagom.scaladsl.api.{Service, ServiceCall}
import play.api.libs.json.{Format, Json}

object HellolagomService  {
  val TOPIC_NAME = "greetings"
}

/**
  * The hello-lagom service interface.
  * <p>
  * This describes everything that Lagom needs to know about how to serve and
  * consume the HellolagomService.
  */

case class Student(id: Int, name: String, college: String)

object Student{
  implicit val format: Format[Student] = Json.format[Student]
}

trait HellolagomService extends Service {

  /**
    * Example: curl http://localhost:9000/api/hello/Alice
    */

  def deleteStudent(id: Int): ServiceCall[NotUsed, ListBuffer[Student]]

  def postStudent(): ServiceCall[Student, ListBuffer[Student]]

  def getStudent(id: Int): ServiceCall[NotUsed, ListBuffer[Student]]

  def updateStudent(id: Int): ServiceCall[Student, ListBuffer[Student]]



  override final def descriptor = {
    import Service._
    // @formatter:off


    named("hello-lagom")
      .withCalls(
        restCall(Method.GET,"/api/studentDetails/:id", getStudent _),
        restCall(Method.POST,"/api/addStudent", postStudent _),
        restCall(Method.DELETE,"/api/deleteStudent/:id", deleteStudent _),
        restCall(Method.PUT, "/api/updateStudent/:id", updateStudent _)
      )
      .withAutoAcl(true)
    // @formatter:on
  }
}

