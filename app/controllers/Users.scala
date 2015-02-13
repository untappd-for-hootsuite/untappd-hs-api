package controllers

import play.api.Play
import play.api.libs.json._
import play.api.libs.json.Reads._
import play.api.libs.functional.syntax._
import play.api.mvc.{Controller, Action}
import com.ning.http.client.AsyncHttpClient
import play.api.Play.current
import models.Checkin

object Users extends Controller {
  def checkins(username: String) = Action { request =>
    val clientId = Play.application.configuration.getString("untappd.client_id").getOrElse("")
    val clientSecret = Play.application.configuration.getString("untappd.client_secret").getOrElse("")
    val accessToken = "A8FDF1C54B97E1B86FCBA74105E9A1CDB48278BF"

    println("Client ID: "+clientId)
    println("Client Secret: "+clientSecret)

    val client = new AsyncHttpClient()
    //val get = client.prepareGet(s"http://api.untappd.com/v4/user/checkins/$username?client_id=$clientId&client_secret=$clientSecret")
    val get = client.prepareGet(s"http://api.untappd.com/v4/checkin/recent?access_token=$accessToken")
    val future = get.execute()

    val resp = future.get()

    Ok(Json.parse(resp.getResponseBody))
  }
}
