package controllers

import play.api.Play
import play.api.libs.json.Json
import play.api.mvc.{Controller, Action}
import com.ning.http.client.AsyncHttpClient
import play.api.Play.current

object Users extends Controller {
  def checkins(username: String) = Action { request =>
    val clientId = Play.application.configuration.getString("untappd.client_id").getOrElse("")
    val clientSecret = Play.application.configuration.getString("untappd.client_secret").getOrElse("")

    println("Client ID: "+clientId)
    println("Client Secret: "+clientSecret)

    val client = new AsyncHttpClient()
    val get = client.prepareGet(s"http://api.untappd.com/v4/user/checkins/tobyjsullivan?client_id=$clientId&client_secret=$clientSecret")
    val future = get.execute()

    val resp = future.get()

    Ok(Json.parse(resp.getResponseBody))
  }
}
