package com.example.ScalaProject.services.main.impl

import cats.effect.IO
import com.example.ScalaProject.dao.UserDAO
import com.example.ScalaProject.exceptions.Exceptions.NotFoundException
import com.example.ScalaProject.models.User
import com.example.ScalaProject.services.helpers.{BCryptHelper, UUIDHelper}
import com.example.ScalaProject.services.main.UserService


class UserServiceImpl(userDAO: UserDAO,
                      bCryptHelper: BCryptHelper,
                     )
                     (implicit uuid: UUIDHelper)
                      extends UserService {

  override def createUser(email: String, password: String): IO[String] = {
    val id = uuid.generate
    val user = User(
      id = id,
      email = email,
      password = Some(bCryptHelper.encrypt(password)),
    )
    userDAO.createOne(user)
  }

  override def getByEmail(email: String): IO[User] =
    userDAO.getByEmail(email).flatMap(getOrThrow)

  def getOrThrow(maybeUser: Option[User]): IO[User] =
    maybeUser match {
      case Some(user) => IO.pure(user)
      case None => IO.raiseError(NotFoundException("User not found"))
    }


}
