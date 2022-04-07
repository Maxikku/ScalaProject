package com.example.ScalaProject

package services.helpers
package impl

import com.example.ScalaProject.config.JwtConfig
import com.example.ScalaProject.models.User
import pdi.jwt.{Jwt, JwtAlgorithm, JwtCirce, JwtClaim}

import scala.util.{Failure, Success, Try}
import scala.concurrent.duration.DurationLong
import java.time.Clock

class JwtHelperImpl(jwtConfig: JwtConfig)(implicit clock: Clock)  extends JwtHelper {

  override def encode(user: User,ttl: Option[Long] = None): String = {
    val tokenClaims = JwtClaim(user.id)
      .issuedNow.expiresIn(ttl.getOrElse(jwtConfig.ttl).days.toSeconds)
    Jwt.encode(tokenClaims, jwtConfig.secret, JwtAlgorithm.HS256)
  }

  override def decode(token: String): Try[String] = {
    JwtCirce.decodeJson(token, jwtConfig.secret, Seq(JwtAlgorithm.HS256))
      .flatMap(_.as[String] match {
        case Left(failure) => Failure(failure)
        case Right(body) => Success(body)
      })
  }

}