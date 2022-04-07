package com.example.ScalaProject

import cats.effect.Concurrent
import cats.implicits.catsSyntaxApplicativeId
import io.circe.generic.semiauto.{deriveDecoder, deriveEncoder}
import io.circe.{Decoder, Encoder}
import org.http4s.circe.{jsonEncoderOf, jsonOf}
import org.http4s.{EntityDecoder, EntityEncoder}


trait Movies[F[_]] {
  def getMovies: F[Movies.Movie]
}

object Movies {
  def apply[F[_]](implicit ev: Movies[F]): Movies[F] = ev

  final case class Movie(id: String, title: String, year: Int) /*, director: String*/

  object Movie {
    implicit val movieDecoder: Decoder[Movie] = deriveDecoder[Movie]
    implicit def movieEntityDecoder[F[_]: Concurrent]: EntityDecoder[F, Movie] =
      jsonOf
    implicit val movieEncoder: Encoder[Movie] = deriveEncoder[Movie]
    implicit def movieEntityEncoder[F[_]]: EntityEncoder[F, Movie] =
      jsonEncoderOf
  }

  val movies = Movies.Movie(
    "6bcbca1e-efd3-411d-9f7c-14b872444fce",
    "Zack Snyder's Justice League",
    2021,
  )

  final case class JokeError(e: Throwable) extends RuntimeException

  def impl[F[_]: Concurrent]: Movies[F] = new Movies[F]{

    def getMovies: F[Movies.Movie] = movies.pure[F]

  }
}


