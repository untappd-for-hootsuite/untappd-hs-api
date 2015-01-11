package controllers.auth

import play.api.Play
import play.api.Play.current
import play.api.mvc.Request

object AuthTokenManager {
  val tokenHeader = "X-Auth-Token"
}

trait AuthTokenManager {

  private val authTokenOverride = Play.application.configuration.getString("untappd.token_override")

  def getUntappdAuthToken(implicit request: Request[_]): Option[String] = authTokenOverride match {
    case Some(_) => authTokenOverride
    case None =>
      // Check request header for auth token
      request.headers.get(AuthTokenManager.tokenHeader)
  }
}
