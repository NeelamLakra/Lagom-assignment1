package com.example.hello.impl

import akka.NotUsed
import com.example.hello.api._
import com.lightbend.lagom.scaladsl.api.ServiceCall

import scala.collection.mutable.ListBuffer
import scala.concurrent.{ExecutionContext, Future}

/**
  * Implementation of the HellolagomService.
  */
class HellolagomServiceImpl (implicit ec: ExecutionContext)extends HellolagomService {


  val StudentList = new ListBuffer[Student]


  override def getStudent(id: Int): ServiceCall[NotUsed, ListBuffer[Student]] = ServiceCall {
    request =>
      Future.successful(StudentList.filter(elem=> elem.id == id))
  }

  override def postStudent(): ServiceCall[Student, ListBuffer[Student]] = ServiceCall {
    request =>
      val add = Student(request.id, request.name, request.college)
      StudentList += add
      Future.successful(StudentList)
  }

  override def deleteStudent(id: Int): ServiceCall[NotUsed, ListBuffer[Student]] = ServiceCall {
    request =>
      val deleteRecord = StudentList.filter(ele => ele.id == id)
      StudentList --= deleteRecord
      Future.successful(StudentList)
  }


  override def updateStudent(id: Int): ServiceCall[Student, ListBuffer[Student]] = ServiceCall {
    request =>
      val deleteRecord = StudentList.filter(ele => ele.id == id)
      StudentList --= deleteRecord
      val add = Student(request.id, request.name, request.college)
      StudentList += add
      Future.successful(StudentList)
  }

}
