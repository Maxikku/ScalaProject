package com.example.ScalaProject.dao.reactiveMongo

import cats.effect.IO
import com.example.ScalaProject.config.ApplicationConfig
import com.example.ScalaProject.dao.TokenDAO
import reactivemongo.api.MongoConnection
import com.example.ScalaProject.models.Token
import reactivemongo.api.bson.BSONDocument

import scala.concurrent.ExecutionContext

class TokenDAOImpl(config: ApplicationConfig, connection: MongoConnection)(implicit ex: ExecutionContext)
  extends ReactiveMongoDAOImpl[Token](config.mongo.dbName, "token", connection)
    with TokenDAO {
  override def getByBody(body: String): IO[Option[Token]] = withCollection {
    _.find(BSONDocument("body" -> body)).one[Token]
  }

  def getByUserId(userId: String): IO[Option[Token]] = withCollection {
    _.find(BSONDocument("userId" -> userId)).one[Token]
  }
}
