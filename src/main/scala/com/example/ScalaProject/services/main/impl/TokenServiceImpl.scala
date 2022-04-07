package com.example.ScalaProject
package services.main.impl

import cats.effect.IO
import com.example.ScalaProject.config.TokenConfig
import com.example.ScalaProject.dao.TokenDAO
import com.example.ScalaProject.exceptions.Exceptions.NotFoundException
import com.example.ScalaProject.models.Token
import com.example.ScalaProject.services.helpers.UUIDHelper
import com.example.ScalaProject.services.main.TokenService

import java.time.{Clock, Instant}
import java.time.temporal.ChronoUnit

class TokenServiceImpl(tokenDao: TokenDAO, tokenConfig: TokenConfig)(implicit clock: Clock,
                                                                     uuid: UUIDHelper) extends TokenService {
  override def createToken(userId: String): IO[Token] =
  {
    val body = uuid.generate
    val token = Token(
      uuid.generate,
      userId,
      body,
      Instant.now(clock),
    )
    tokenDao.getByUserId(userId)
      .flatMap {
        case Some(tkn) => tokenDao.update(token.copy(id = tkn.id))
          .flatMap(_ => IO.pure(token))
        case None => tokenDao.createOne(token)
          .flatMap(_ => IO.pure(token))
      }
  }

  override def getByBody(body: String): IO[Token] =
    tokenDao.getByBody(body).flatMap {
      case Some(token) => IO.pure(token)
      case None => IO.raiseError(NotFoundException("Token not found"))
    }
}