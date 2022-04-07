package com.example.ScalaProject
package dao.reactiveMongo

import cats.effect.{Async, IO}
import reactivemongo.api.bson.collection.BSONCollection
import dao.DAO

import java.util.UUID
import scala.concurrent.{ExecutionContext, Future}
import exceptions.Exceptions._
import models.HasId

import reactivemongo.api.bson.BSONDocument
import reactivemongo.api.{AsyncDriver, DB, MongoConnection}
import reactivemongo.api.bson.collection.BSONSerializationPack._




class ReactiveMongoDAOImpl[M <: HasId[String] : Reader : Writer](dbName: String,
                                                                 collectionName: String,
                                                                 connection: MongoConnection)(implicit ex: ExecutionContext)
  extends DAO[String, M] {

  def db: IO[DB] = IO.fromFuture(IO(connection.database(dbName)))

  def collectionIO: IO[BSONCollection] = db.map(_.collection[BSONCollection](collectionName))

  def withCollection[B](p: BSONCollection => Future[B]): IO[B] = collectionIO.flatMap {
    collection => IO.fromFuture(IO(p(collection)))
  }

  override def createOne(model: M): IO[String] =
    withCollection(_.insert.one(model).map(_.code))
      .flatMap {
        case None => IO.pure(model.id)
        case Some(errCode) => IO.raiseError(MongoException(errCode))
      }

  override def update(model: M): IO[Boolean] = update(model, upsert = true)


  def getMany(filter: ElementProducer*): IO[Seq[M]] = getManyFilter(BSONDocument(filter: _*))

  def getManyFilter(filter: BSONDocument): IO[Seq[M]] = withCollection {
    _.find(filter)
      .cursor[M]()
      .collect[Seq]()
  }

  def update(model: M, upsert: Boolean = false): IO[Boolean] = withCollection(
    _.update(ordered = false)
      .one(BSONDocument("_id" -> model.id), model, upsert, multi = false)
      .map(_.nModified == 1)
  )

  def updateSet(id: String, updateBody: ElementProducer*): IO[Boolean] = withCollection { collection =>
    val updateModifier = BSONDocument(
      "$set" -> BSONDocument(
        updateBody: _*
      )
    )

    collection.findAndUpdate(
      selector = BSONDocument("_id" -> id),
      update = updateModifier,
    ).map(_.lastError.isEmpty)
  }

}

object ReactiveMongoDAOImpl {
  def getConnection(mongoUri: String)(implicit ex: ExecutionContext): IO[MongoConnection] = IO.fromFuture(IO {
    for {
      uri <- MongoConnection.fromString(mongoUri)
      driver: AsyncDriver = AsyncDriver()
      connection <- driver.connect(uri)
    } yield connection
  })
}
