package com.example.ScalaProject.services

import cats.effect.IO
import com.example.ScalaProject.config.ApplicationConfig
import com.example.ScalaProject.dao.{DAOs, UserDAO}
import com.example.ScalaProject.services.helpers.{BCryptHelper, JwtHelper}
import com.example.ScalaProject.services.helpers.impl.{BCryptHelperImpl, JwtHelperImpl, UUIDHelperImpl}
import com.example.ScalaProject.services.main.{AuthService, TokenService, UserService}
import com.example.ScalaProject.services.main.impl.{AuthServiceImpl, TokenServiceImpl, UserServiceImpl}
import com.example.ScalaProject.services.saas.EmailService
import com.example.ScalaProject.services.saas.impl.EmailServiceImpl

import java.time

final case class Services(
                           authService: AuthService,
                           userService: UserService,
                           tokenService: TokenService,
                           bcryptHelper: BCryptHelper,
                           jwtHelper: JwtHelper,
                           emailService: EmailService,
                         )

object Services {
  def create(appConfig: ApplicationConfig, daos: DAOs): IO[Services] = {
    implicit val clock: time.Clock = java.time.Clock.systemUTC()
    implicit val uuid: UUIDHelperImpl = new UUIDHelperImpl

    val tokenService = new TokenServiceImpl(daos.tokenDAO, appConfig.token)
    val bcryptHelper = new BCryptHelperImpl(appConfig.bcrypt)
    val userService = new UserServiceImpl(daos.userDAO, bcryptHelper)
    val jwtHelper = new JwtHelperImpl(appConfig.jwt)
    val emailService = new EmailServiceImpl(appConfig.email)

    val authService = new AuthServiceImpl(
      tokenService,
      userService,
      emailService,
      bcryptHelper,
      jwtHelper,
    )

    IO.pure(Services(
      authService = authService,
      userService = userService,
      tokenService = tokenService,
      bcryptHelper = bcryptHelper,
      jwtHelper = jwtHelper,
      emailService = emailService,


//      fileHelper = new ProjectFileHelper {},

//      organisationService = organisationService,
//      fleetService = fleetService,
//      pushService = pushService,
//      fmsIntegrationService
    ))
  }
}

