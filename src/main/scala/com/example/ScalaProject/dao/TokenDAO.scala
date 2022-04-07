package com.example.ScalaProject
package dao

import models.Token
import cats.effect.IO

trait TokenDAO extends DAO[String, Token] {
  def getByBody(body: String): IO[Option[Token]]

  def getByUserId(userId: String): IO[Option[Token]]
}
