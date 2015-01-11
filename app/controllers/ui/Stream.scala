package controllers.ui

import play.api.Play
import play.api.mvc.{Cookie, Controller, Action}
import play.api.Play.current


object Stream extends Controller {
  private val authTokenOverride = Play.application.configuration.getString("untappd.token_override")

  def default(paramToken: Option[String]) = Action { request =>
    val authTokenCookieKey = "untappd-token"

    // First check for a token override for dev environments
    // Otherwise check for a token param
    // Finally try reading token from cookie
    val oToken = (authTokenOverride, paramToken) match {
      case (Some(token), _) => Some(token)
      case (None, Some(token)) => Some(token)
      case (None, None) => request.cookies.get(authTokenCookieKey).map(_.value)
    }

    oToken match {
      case None =>
        // No token means unauthorised. Just redirect to login.
        Redirect("/login")
      case Some(token) =>
        Ok(views.html.stream(token)).withCookies(Cookie(authTokenCookieKey, token))
    }
  }
}
