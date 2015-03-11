package controllers

import controllers.auth.AuthTokenManager
import play.api.Play.current
import play.api.libs.ws._
import play.api.mvc.{Action, Controller}

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

object Checkins extends Controller with AuthTokenManager {
  def recent = Action.async { implicit request =>
    val oAuthToken = getUntappdAuthToken

    oAuthToken match {
      case None => Future(Unauthorized(s"Request must include valid ${AuthTokenManager.tokenHeader} header"))
      case Some(authToken) =>
        val holder: WSRequestHolder = WS.url(s"https://api.untappd.com/v4/checkin/recent")
          .withQueryString("access_token" -> authToken)

        holder.get().map { resp =>
          Ok(resp.json)
        }

    }
  }

  def toast(checkinId: String) = Action.async { implicit request =>
    val oAuthToken = getUntappdAuthToken

    println(s"Calling toast on $checkinId")

    oAuthToken match {
      case None => Future(Unauthorized(s"Request must include valid ${AuthTokenManager.tokenHeader} header"))
      case Some(authToken) =>
        val holder: WSRequestHolder = WS.url(s"https://api.untappd.com/v4/checkin/toast/$checkinId")
          .withQueryString("access_token" -> authToken)

        holder.get().map { resp =>
          println(resp.json)
          Ok(resp.json)
        }
    }
  }

  def comment(checkinId: String) = Action.async { implicit request =>
    val comment = request.getQueryString("comment")
    val oAuthToken = getUntappdAuthToken

    oAuthToken match {
      case None => Future(Unauthorized(s"Request must include valid ${AuthTokenManager.tokenHeader} header"))
      case Some(authToken) =>
        val holder: WSRequestHolder = WS.url(s"https://api.untappd.com/v4/checkin/addcomment/$checkinId")
          .withQueryString("access_token" -> authToken)

        holder.post(Map("comment" -> Seq(comment.get))).map { resp =>
          println(resp.json)
          Ok(resp.json)
        }
    }
  }
}
