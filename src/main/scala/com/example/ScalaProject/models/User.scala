package com.example.ScalaProject
package models

import reactivemongo.api.bson.Macros.Annotations.Key
import reactivemongo.api.bson.{BSONDocumentHandler, Macros}

import java.time.Instant

final case class User(@Key("_id") id: String,
                      email: String,
                      password: Option[String] = None,
                     ) extends HasId[String]


object User {
  implicit val handler: BSONDocumentHandler[User] = Macros.handler[User]
}
