package com.example.ScalaProject
package services.helpers

trait BCryptHelper {
  def encrypt(password: String): String

  def compare(plainTextPass: String, hash: String): Boolean
}
