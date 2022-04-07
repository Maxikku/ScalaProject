package com.example.ScalaProject
package exceptions

object Exceptions {
  class AppException(msg: String) extends RuntimeException(msg)
  case class MongoException(code: Int) extends AppException(s"mongo exception. Code: $code")
  case class NotFoundException(text: String) extends AppException(text)
  case object WrongCredentialsException extends AppException("Wrong credentials")
}
