package com.example.ScalaProject

import cats.effect.kernel.Resource
import cats.effect.{ExitCode, IO, IOApp}
import cats.implicits.catsSyntaxApplicativeId
import com.example.ScalaProject.config.ApplicationConfig
import com.example.ScalaProject.controllers.Controller
import com.example.ScalaProject.dao.DAOs
import com.example.ScalaProject.dao.reactiveMongo.ReactiveMongoDAOImpl
import com.example.ScalaProject.services.Services
import org.typelevel.log4cats.slf4j.Slf4jLogger

import scala.concurrent.ExecutionContext


object Main extends IOApp{
  override def run(args: List[String]): IO[ExitCode] = {
    val appConfig = ApplicationConfig.load()
    import ExecutionContext.Implicits.global

    val resources = for {
      log <- Resource.eval(Slf4jLogger.fromClass[IO](getClass))
      _ <- Resource.eval(log.info("Initializing Db"))
      connection <- Resource.eval(ReactiveMongoDAOImpl.getConnection(appConfig.mongo.uri))
    } yield (log, connection)

    resources.use {
      case (log, connection) =>
        for {
          _ <- log.info("Initializing services")
          daos <- DAOs.create(appConfig, connection)
          services <- Services.create(appConfig, daos)
          routes <- Controller(services).pure[IO]
          _ <- log.info("Starting Http server")
          exitCode <- HttpServer.makeAndStart(appConfig.http, routes)
        } yield exitCode
    }


  }
}

