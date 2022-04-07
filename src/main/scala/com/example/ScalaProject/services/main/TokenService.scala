package com.example.ScalaProject.services.main

import cats.effect.IO
import com.example.ScalaProject.models.Token

trait TokenService {

  def createToken(userId: String): IO[Token]

  def getByBody(body: String): IO[Token]

}
