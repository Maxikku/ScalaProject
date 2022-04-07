package com.example.ScalaProject.services.main

import cats.effect.IO
import com.example.ScalaProject.models.dto.AuthResponse

trait AuthService {
//  def createPassword(token: String, password: String): IO[Unit]
  def signUp(email: String, password: String): IO[Unit]
  def signIn(email: String, password: String): IO[AuthResponse]
}
