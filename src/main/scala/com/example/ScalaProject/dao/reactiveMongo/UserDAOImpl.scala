package com.example.ScalaProject
package dao.reactiveMongo

import cats.effect.IO
import dao.UserDAO
import models.User
import config.ApplicationConfig
import reactivemongo.api.MongoConnection
import reactivemongo.api.bson.BSONDocument

import java.time.Instant
import scala.concurrent.ExecutionContext

class UserDAOImpl(config: ApplicationConfig, connection: MongoConnection)(implicit ex: ExecutionContext)
  extends ReactiveMongoDAOImpl[User](config.mongo.dbName, "users", connection)
    with UserDAO {

  override def getByEmail(email: String): IO[Option[User]] = withCollection {
    _.find(BSONDocument("email" -> email)).one[User]
  }

}
