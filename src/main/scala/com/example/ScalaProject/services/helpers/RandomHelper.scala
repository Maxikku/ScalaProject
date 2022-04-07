package com.example.ScalaProject.services.helpers

import scala.util.Random

trait RandomHelper {
  def generate: String
}

object RandomHelper {
  implicit val random: RandomHelper = new RandomHelper {
    override def generate: String = Random.between(100000, 1000000).toString
  }
}

