package com.example.ScalaProject.services.saas

import cats.effect.IO

import java.io.File

trait EmailService {
  def sendEmail(title: String, emailTo: String, contentHtml: String, files: List[File]): IO[Unit]
}
