package com.example.ScalaProject.helpers

import org.scalamock.scalatest.MockFactory
import org.scalatest.OneInstancePerTest

import scala.concurrent.ExecutionContext
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.wordspec.AnyWordSpec

trait CommonTest extends AnyWordSpec
  //  with OneInstancePerTest
  with MockFactory {

  implicit val ex: ExecutionContext = ExecutionContext.global
  //  implicit val sch: Scheduler = Scheduler.Implicits.global

}
