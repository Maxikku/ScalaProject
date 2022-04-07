package com.example.ScalaProject

import cats.effect.{Async, ExitCode}
import com.example.ScalaProject.config.HttpConfig
import org.http4s.HttpApp
import org.http4s.blaze.server.BlazeServerBuilder

import scala.concurrent.duration.DurationInt


object HttpServer {
  def makeAndStart[F[_] : Async](httpConfig: HttpConfig, routes: HttpApp[F]): F[ExitCode] =
    BlazeServerBuilder[F]
      .withResponseHeaderTimeout(2.minutes)
      .withIdleTimeout(3.minutes)
      .bindHttp(httpConfig.port, httpConfig.host)
      .withHttpApp(routes)
      .withoutBanner
      .serve
      .compile
      .lastOrError

}
