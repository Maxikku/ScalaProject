package com.example.ScalaProject.config

import com.typesafe.config.ConfigFactory
import pureconfig.ConfigSource
import pureconfig.generic.auto._

final case class ApplicationConfig(
                                    http: HttpConfig,
                                    mongo: MongoConfig,
                                    email: EmailConfig,
                                    token: TokenConfig,
                                    bcrypt: BcryptConfig,
                                    jwt: JwtConfig,
                                  )

object ApplicationConfig {
  def load(): ApplicationConfig =
    ConfigSource.fromConfig(ConfigFactory.load()).loadOrThrow[ApplicationConfig]
}
