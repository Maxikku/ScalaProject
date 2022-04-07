package com.example.ScalaProject.controllers

import cats.effect.IO
import com.example.ScalaProject.controllers.impl.AuthController
import com.example.ScalaProject.services.Services
import com.example.ScalaProject.services.helpers.JwtHelper
import org.http4s.dsl.Http4sDsl
import org.http4s.server.Router
import org.http4s.server.middleware.{AutoSlash, CORS, RequestLogger, ResponseLogger, Timeout}
import org.http4s.{EntityDecoder, HttpApp, HttpRoutes, Request, Response}
import org.scalatest.time.SpanSugar.convertIntToGrainOfTime


trait Controller {
  val dsl: Http4sDsl[IO] = Http4sDsl[IO]
  import dsl._

  object TokenParam extends QueryParamDecoderMatcher[String]("token")

  def routes: HttpRoutes[IO]

  def parseAndHandleError[M](req: Request[IO])(io: M => IO[Response[IO]])(implicit decoder: EntityDecoder[IO, M]): IO[Response[IO]] = for {
    body <- req.as[M]
    resp <- io(body)
  } yield resp

}

object Controller {
  val dsl: Http4sDsl[IO] = Http4sDsl[IO]

  def apply(services: Services): HttpApp[IO] = {
    val middleware: HttpRoutes[IO] => HttpRoutes[IO] =
      ((http: HttpRoutes[IO]) => AutoSlash(http))
        .andThen(http => CORS.policy.httpRoutes(http))
        .andThen(http => Timeout(30.seconds)(http))

    implicit val jwtHelper: JwtHelper = services.jwtHelper

    val loggers: HttpApp[IO] => HttpApp[IO] = {
      { http: HttpApp[IO] =>
        RequestLogger.httpApp(logHeaders = false, logBody = false)(http)
      }.andThen { http: HttpApp[IO] =>
        ResponseLogger.httpApp(logHeaders = false, logBody = false)(http)
      }
    }

    val authController = new AuthController(services.authService, services.tokenService)

    val routes = Router("api/v1" -> Router(
      "auth" -> authController.routes
    ))

    loggers(middleware(routes).orNotFound)
  }
}
