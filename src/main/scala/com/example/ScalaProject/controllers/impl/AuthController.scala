package com.example.ScalaProject.controllers.impl

import cats.effect.IO
import com.example.ScalaProject.controllers.Controller
import com.example.ScalaProject.models.dto.CredentialsDTO
import com.example.ScalaProject.services.helpers.JwtHelper
import com.example.ScalaProject.services.main.{AuthService, TokenService}
import org.http4s.HttpRoutes
import io.circe.generic.auto._
import org.http4s.circe.CirceEntityDecoder._
import org.http4s.circe.CirceEntityEncoder._

class AuthController(authService: AuthService,
                     tokenService: TokenService)
                    (implicit jwtHelper: JwtHelper) extends Controller {

  import dsl._

  override def routes: HttpRoutes[IO] = HttpRoutes.of[IO] {

    case req@POST -> Root / "signUp" => parseAndHandleError[CredentialsDTO](req) { credentials =>
      authService
        .signUp(credentials.email, credentials.password)
        .flatMap(Ok(_))

    }
    case req@POST -> Root / "signIn" => parseAndHandleError[CredentialsDTO](req) { credentials =>
      authService
        .signIn(credentials.email, credentials.password)
        .flatMap(Ok(_))

    }

  }
}
