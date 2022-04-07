package com.example.ScalaProject.dao

import cats.effect.IO
import com.example.ScalaProject.config.ApplicationConfig
import com.example.ScalaProject.dao.reactiveMongo.{TokenDAOImpl, UserDAOImpl}
import reactivemongo.api.MongoConnection

import scala.concurrent.ExecutionContext

final case class DAOs(
                       userDAO: UserDAO,
                       tokenDAO: TokenDAO,
                     )

object DAOs {
  def create(config: ApplicationConfig,
             connection: MongoConnection)(implicit ex: ExecutionContext): IO[DAOs] = {
    IO.pure(DAOs(
      new UserDAOImpl(config, connection),
      new TokenDAOImpl(config, connection),
    ))
  }
}
