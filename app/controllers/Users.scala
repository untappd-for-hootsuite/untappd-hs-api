package controllers

import play.api.Play
import play.api.libs.json._
import play.api.mvc.{Controller, Action}
import com.ning.http.client.AsyncHttpClient
import play.api.Play.current

import scala.collection.mutable

class User (val id: Int, val first_name: String, val last_name: String, val user_name: String, val avatar: String)
class Beer (val id: Int, val name: String, val logo: String, val brewery: String, val brewery_id: Int)
class Toast (val id: Int, val user: User, val created_at: String)
class Comment (val id: Int, val user: User, val comment: String, val created_at: String)
class Checkin (val id: Int, val user: User, val beer: Beer, val toasts: List[Toast], val comments: List[Comment],
               val user_comment: String, val rating: Int, val created_at: String)

object Users extends Controller {
  def checkins(username: String) = Action { request =>
    val clientId = Play.application.configuration.getString("untappd.client_id").getOrElse("")
    val clientSecret = Play.application.configuration.getString("untappd.client_secret").getOrElse("")

    println("Client ID: "+clientId)
    println("Client Secret: "+clientSecret)

    val client = new AsyncHttpClient()
    val get = client.prepareGet(s"http://api.untappd.com/v4/user/checkins/$username?client_id=$clientId&client_secret=$clientSecret")
    val future = get.execute()

    val resp = future.get()

    Ok(Json.parse(resp.getResponseBody))
  }


  def my_checkins() = Action { request =>
    val accessToken = "A8FDF1C54B97E1B86FCBA74105E9A1CDB48278BF"

    val client = new AsyncHttpClient()
    val get = client.prepareGet(s"http://api.untappd.com/v4/checkin/recent?access_token=$accessToken")
    val future = get.execute()

    val resp = future.get()

    val json = Json.parse(resp.getResponseBody).as[JsObject]
    val apiResponse = json

    val apiCheckins = json.value("response").as[JsObject].value("checkins").as[JsObject].value("items").as[List[JsObject]]

    val modelCheckins = mutable.MutableList[Checkin]()
    for (checkin <- apiCheckins) {
      println(checkin.keys)
      val jsonUser = checkin.value("user").as[JsObject]

      val modelUser = new User (
        jsonUser.value("uid").as[Int],
        jsonUser.value("first_name").as[String],
        jsonUser.value("last_name").as[String],
        jsonUser.value("user_name").as[String],
        jsonUser.value("user_avatar").as[String]
      )

      val jsonBeer = checkin.value("beer").as[JsObject]
      val jsonBrewery = checkin.value("brewery").as[JsObject]

      val modelBeer = new Beer (
        jsonBeer.value("bid").as[Int],
        jsonBeer.value("beer_name").as[String],
        jsonBeer.value("beer_label").as[String],
        jsonBrewery.value("brewery_name").as[String],
        jsonBrewery.value("brewery_id").as[Int]
      )

      val modelCheckin = new Checkin (
        checkin.value("checkin_id").as[Int],
        modelUser,
        modelBeer,
        List[Toast](),
        List[Comment](),
        checkin.value("checkin_comment").as[String],
        (checkin.value("rating_score").as[Float]*10).asInstanceOf[Int],
        checkin.value("created_at").as[String]
      )
      print(modelCheckin.id)
      modelCheckins.++=(mutable.MutableList[Checkin](modelCheckin))
    }
    println(modelCheckins)

    Ok(views.html.streams(modelCheckins))
    //Ok(apiResponse)
  }
}
