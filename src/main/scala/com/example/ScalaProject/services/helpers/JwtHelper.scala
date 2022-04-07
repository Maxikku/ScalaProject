package com.example.ScalaProject.services.helpers

import com.example.ScalaProject.models.User
import scala.util.Try


trait JwtHelper {
  def encode(user: User, ttl: Option[Long] = None): String

  def decode(token: String): Try[String]
}
