package com.example.ScalaProject
package dao

import cats.effect.IO
import com.example.ScalaProject.models.User

//import java.time.Instant


trait UserDAO extends DAO[String, User]{
  def getByEmail(email: String): IO[Option[User]]
}
