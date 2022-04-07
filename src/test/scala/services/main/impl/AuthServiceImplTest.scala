//package services.main.impl
//
//import cats.effect.IO
//import cats.effect.unsafe.implicits.global
//import com.example.ScalaProject.config.JwtConfig
//import com.example.ScalaProject.helpers.CommonTest
//import com.example.ScalaProject.models.{Token, User}
//import com.example.ScalaProject.services.helpers.{BCryptHelper, JwtHelper}
//import com.example.ScalaProject.services.main.impl.AuthServiceImpl
//import com.example.ScalaProject.services.main.{TokenService, UserService}
//import com.example.ScalaProject.services.saas.EmailService
//
//import java.time.{Instant, ZoneOffset}
//import scala.concurrent.duration.DurationInt
//import org.scalatest.matchers.should.Matchers._
//
//
//class AuthServiceImplTest extends CommonTest{
//  val tokenServiceMock: TokenService = mock[TokenService]
//  val emailServiceMock: EmailService = mock[EmailService]
//  val userServiceMock: UserService = mock[UserService]
//  val bcryptHelperMock: BCryptHelper = mock[BCryptHelper]
//
//  implicit val clock: java.time.Clock = java.time
//    .Clock.fixed(Instant.parse("2018-08-22T10:00:00Z"), ZoneOffset.UTC)
//
//  val jwtConfig: JwtConfig = JwtConfig(ttl = 2.minutes.toMillis, secret = "scr")
//  val jwtHelperMock: JwtHelper = mock[JwtHelper]
//
//  val authServiceImpl: AuthServiceImpl = new AuthServiceImpl(
//    tokenServiceMock,
//    userServiceMock,
//    emailServiceMock,
//    bcryptHelperMock,
//    jwtHelperMock,
//  )
//
//  val user: User = User(
//    id = "userid",
//    email = "fleetManager@gmail.com",
////    password = Some("passwordhash"),
//  )
//
//
//  "signUp" should {
//
//    val email = "email"
//    val userId = "userId"
//    val token = Token(
//      id = "id",
//      userId = userId,
//      body = "body",
//      timestamp = Instant.now(clock)
//    )
//
//    "normally create user" in {
//      userServiceMock.createUser _ expects(email) returning IO.pure(userId)
//      tokenServiceMock.createOrUpdateEmail _ expects userId returning IO.pure(token)
//      emailServiceMock.sendEmail _ expects(*, email, token.body, List.empty) returning IO.unit
//
//      authServiceImpl.signUp(email)
//        .unsafeRunSync() should be()
//    }

//    "organisation doesn't exist" in {
//      organisationServiceMock.exists _ expects organisationId returning IO.pure(false)
//
//      an[NotFoundException] should be thrownBy authServiceImpl.createFleetManager(email, organisationId, firstName, lastName, List.empty, canCreateFleets = true)
//        .unsafeRunSync()
//    }
//
//    "email is taken" in {
//      organisationServiceMock.exists _ expects organisationId returning IO.pure(true)
//      userServiceMock.createFleetManager _ expects(email, organisationId, firstName, lastName, List.empty, true) returning IO.raiseError(ForbiddenException(""))
//
//      an[ForbiddenException] should be thrownBy authServiceImpl.createFleetManager(email, organisationId, firstName, lastName, List.empty, canCreateFleets = true)
//        .unsafeRunSync()
//    }
//  }


//}
