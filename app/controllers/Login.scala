package controllers

import controllers.Checkins._
import controllers.auth.AuthTokenManager
import play.api.libs.ws.{WS, WSRequestHolder}
import play.api.mvc.{Action, Controller}

import scala.concurrent.Future

object Login extends Controller with AuthTokenManager {
  def redirect = Action {
    Redirect("https://untappd.com/oauth/authenticate/", Map(
      "client_id" -> Seq("DF03C2F0EAA8CAE333427AD75EA3E179F7E2A920"),
      "response_type" -> Seq("code"),
      "redirect_url" -> Seq("https://untappdforhootsuite-auth.herokuapp.com/callback")
    ))
  }
}