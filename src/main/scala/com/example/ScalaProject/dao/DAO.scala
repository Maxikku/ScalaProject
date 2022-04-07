package com.example.ScalaProject
package dao

import cats.effect.IO
import models.HasId

trait DAO[ID, M <: HasId[ID]] {
  def createOne(model: M): IO[ID]

  def update(model: M): IO[Boolean]

}