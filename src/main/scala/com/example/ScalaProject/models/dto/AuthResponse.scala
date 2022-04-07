package com.example.ScalaProject.models.dto

case class AuthResponse(
                         token: String,
                         userId: String,
                         email: String,
                       )
