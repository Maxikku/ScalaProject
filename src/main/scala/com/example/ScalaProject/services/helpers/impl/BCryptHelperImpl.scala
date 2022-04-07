package com.example.ScalaProject
package services.helpers.impl

import com.example.ScalaProject.services.helpers.BCryptHelper
import at.favre.lib.crypto.bcrypt.BCrypt
import com.example.ScalaProject.config.BcryptConfig


class BCryptHelperImpl(bCryptConfig: BcryptConfig) extends BCryptHelper {
  override def encrypt(password: String): String = BCrypt.withDefaults().hashToString(bCryptConfig.rounds, password.toCharArray)

  override def compare(plainTextPass: String, hash: String): Boolean = BCrypt.verifyer().verify(plainTextPass.toCharArray, hash).verified
}
