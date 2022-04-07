package com.example.ScalaProject.services.main.impl

import cats.effect.IO
import com.example.ScalaProject.dao.UserDAO
import com.example.ScalaProject.exceptions.Exceptions.{NotFoundException, WrongCredentialsException}
import com.example.ScalaProject.models.User
import com.example.ScalaProject.models.dto.AuthResponse
import com.example.ScalaProject.services.helpers.{BCryptHelper, JwtHelper, UUIDHelper}
import com.example.ScalaProject.services.main.{AuthService, TokenService, UserService}
import com.example.ScalaProject.services.saas.EmailService

import java.time.{Clock, Instant}

class AuthServiceImpl(tokenService: TokenService,
                      userService: UserService,
                      emailService: EmailService,
                      bCryptHelper: BCryptHelper,
                      jwtHelper: JwtHelper,

                     )
                     (implicit clock: Clock) extends AuthService {

//  override def signUp(email: String, password: String): IO[User] = {
//    userService.create(email, password)
//  }

  override def signUp(email: String, password: String): IO[Unit] =
    for {
      userId <- userService.createUser(email, password)
      token <- tokenService.createToken(userId)
      _ <- emailService.sendEmail("Confirmation", email, token.body, List.empty)
    } yield ()

  override def signIn(email: String, password: String): IO[AuthResponse] = for {
    user <- userService.getByEmail(email)
      .handleErrorWith {
        case _: NotFoundException => IO.raiseError(WrongCredentialsException)
      }
    jwt <- user match {
      case user if bCryptHelper.compare(password, user.password.get) =>
        IO.pure(jwtHelper.encode(user))
      case _ => IO.raiseError(WrongCredentialsException)
    }
  } yield AuthResponse(jwt,user.id,user.email)


//  override def createPassword(tokenString: String, password: String): IO[Unit] = ???
//    for {
//      token <- tokenService.getByBody(tokenString)
//        .handleErrorWith {
//          case _: NotFoundException => IO.raiseError(InvalidTokenException)
//        }
//      user <- if (!tokenService.isEmailTokenExpired(token)) {
//        userService.get(token.userId)
//      } else {
//        IO.raiseError(InvalidTokenException)
//      }
//      _ <- userService.update(user.copy(
//        password = Some(bCryptHelper.encrypt(password)),
//        updatedAt = Instant.now(clock),
//        accountConfirmed = true
//      ))
//    } yield ()
}

