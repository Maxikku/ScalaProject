package com.example.ScalaProject.services.main

import cats.effect.IO
import com.example.ScalaProject.models.User

trait UserService {
  def createUser(email: String, password: String): IO[String]

  def getByEmail(email: String): IO[User]
}
