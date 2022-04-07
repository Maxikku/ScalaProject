val Http4sVersion = "0.23.10"
val CirceVersion = "0.14.1"
val MunitVersion = "0.7.29"
val LogbackVersion = "1.2.10"
val MunitCatsEffectVersion = "1.0.7"
val reactiveMongo = "1.0.10"
val sendGrid = "4.8.3"
val bcrypt = "0.9.0"
val jwtScala = "9.0.4"
val scalaTest = "3.2.11"
val scalaMock = "5.2.0"
val pureConfig = "0.17.1"
val cats = "2.7.0"

val circe = "0.15.0-M1"
//    val http4s       = "1.0.0-M31"
val http4s = "0.23.10"
val log4cats = "2.2.0"
val scalalogging = "3.9.4"

val twilio = "8.27.0"

/////////////////



lazy val root = (project in file("."))
  .settings(
    organization := "com.example",
    name := "ScalaProject",
    version := "0.0.1-SNAPSHOT",
    scalaVersion := "2.13.8",
    libraryDependencies ++= Seq(
      "org.http4s"      %% "http4s-circe"        % Http4sVersion,
      "org.http4s"      %% "http4s-dsl"          % Http4sVersion,
      "org.http4s"      %% "http4s-blaze-server" % Http4sVersion,
      "org.http4s"      %% "http4s-blaze-client" % Http4sVersion,

      "org.reactivemongo" %% "reactivemongo"     % reactiveMongo,
      "com.sendgrid"    % "sendgrid-java"        % sendGrid,
      "com.github.pureconfig" %% "pureconfig"    % pureConfig,
      "at.favre.lib"    % "bcrypt"               % bcrypt,
      "com.github.jwt-scala" %% "jwt-circe"      % jwtScala,
      "org.scalatest"   %% "scalatest"           % scalaTest,
      "org.scalamock"   %% "scalamock"           % scalaMock,
      "org.typelevel"   %% "log4cats-slf4j"      % log4cats,
      "com.typesafe.scala-logging" %% "scala-logging" % scalalogging,
      "com.twilio.sdk"  % "twilio"                % twilio,

      "io.circe"        %% "circe-generic"       % CirceVersion,
      "org.scalameta"   %% "munit"               % MunitVersion           % Test,
      "org.typelevel"   %% "munit-cats-effect-3" % MunitCatsEffectVersion % Test,
      "ch.qos.logback"  %  "logback-classic"     % LogbackVersion         % Runtime,
      "org.scalameta"   %% "svm-subs"            % "20.2.0",
      "org.typelevel"   %% "cats-core"           % cats,
      "io.circe"        %% "circe-core"          % circe,
      "io.circe"        %% "circe-generic"       % circe,
      "io.circe"        %% "circe-parser"        % circe,
    )
  )
