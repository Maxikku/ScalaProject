package com.example.ScalaProject.services.helpers.impl

import com.example.ScalaProject.services.helpers.UUIDHelper

import java.util.UUID

class UUIDHelperImpl extends UUIDHelper {
  override def generate: String = UUID.randomUUID().toString
}

