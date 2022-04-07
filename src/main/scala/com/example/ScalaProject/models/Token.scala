package com.example.ScalaProject.models

import reactivemongo.api.bson.Macros.Annotations.Key
import reactivemongo.api.bson.{BSONDocumentHandler, Macros}

import java.time.Instant

final case class Token(@Key("_id") id: String,
                       userId: String,
                       body: String,
                       timestamp: Instant) extends HasId[String]

object Token {
  implicit val handler: BSONDocumentHandler[Token] = Macros.handler[Token]
}

