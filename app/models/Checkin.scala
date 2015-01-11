package models

import play.api.libs.json._
import play.api.libs.functional.syntax._
import play.api.libs.json.Format
import play.api.libs.json.JsValue
import play.api.libs.json.JsObject
import play.api.libs.json.JsString

case class User (id: Int, first_name: String, last_name: String, user_name: String, avatar: String)
object User {
  implicit object UserFormat extends Format[User] {
    def reads (json: JsValue) = JsSuccess(new User(
      (json \ "uid").as[Int],
      (json \ "first_name").as[String],
      (json \ "last_name").as[String],
      (json \ "user_name").as[String],
      (json \ "user_avatar").as[String]
    ))

    def writes (u: User): JsValue = Json.obj(
      "id" -> u.id,
      "first_name" -> u.first_name,
      "last_name" -> u.last_name,
      "user_name" -> u.user_name,
      "avatar" -> u.avatar
    )
  }
}

case class Beer (id: Int, name: String, label: String)
object Beer {
  implicit object BeerFormat extends Format[Beer] {
    def reads (json: JsValue) = JsSuccess(new Beer(
      (json \ "bid").as[Int],
      (json \ "beer_name").as[String],
      (json \ "beer_label").as[String]
    ))

    def writes (b: Beer): JsValue = Json.obj(
      "id" -> b.id,
      "name" -> b.name,
      "label" -> b.label
    )
  }
}

case class Brewery (id: Int, name: String, country: String, state: String, city: String)
object Brewery {
  implicit object BreweryFormat extends Format[Brewery] {
    def reads (json: JsValue) = JsSuccess(new Brewery(
      (json \ "brewery_id").as[Int],
      (json \ "brewery_name").as[String],
      (json \ "country_name").as[String],
      (json \ "location" \ "brewery_state").as[String],
      (json \ "location" \ "brewery_city").as[String]
    ))

    def writes (b: Brewery): JsValue = Json.obj(
      "id" -> b.id,
      "name" -> b.name,
      "country" -> b.country,
      "state" -> b.state,
      "city" -> b.city
    )
  }
}

case class Toast (id: Int, user: User, created_at: String)
object Toast {
  implicit object ToastFormat extends Format[Toast] {
    def reads (json: JsValue) = JsSuccess(new Toast(
      (json \ "like_id").as[Int],
      (json \ "user").as[User],
      (json \ "created_at").as[String]
    ))

    def writes (t: Toast): JsValue = Json.obj(
      "id" -> t.id,
      "user" -> t.user,
      "created_at" -> t.created_at
    )
  }
}

case class Comment (id: Int, user: User, comment: String, created_at: String)
object Comment {
  implicit object CommentFormat extends Format[Comment] {
    def reads (json: JsValue) = JsSuccess(new Comment(
      (json \ "comment_id").as[Int],
      (json \ "user").as[User],
      (json \ "comment").as[String],
      (json \ "created_at").as[String]
    ))

    def writes (c: Comment): JsValue = Json.obj(
      "id" -> c.id,
      "user" -> c.user,
      "comment" -> c.comment,
      "created_at" -> c.created_at
    )
  }
}
case class Checkin (id: Int, user: User, beer: Beer, brewery: Brewery, toasts: Option[List[Toast]], comments: Option[List[Comment]],
                    user_comment: String, rating: Int, created_at: String)
object Checkin {
  implicit object CheckinFormat extends Format[Checkin] {
    def reads (json: JsValue) = JsSuccess(new Checkin(
      (json \ "checkin_id").as[Int],
      (json \ "user").as[User],
      (json \ "beer").as[Beer],
      (json \ "brewery").as[Brewery],
      (json \ "toasts" \ "items").as[Option[List[Toast]]],
      (json \ "comments" \ "items").as[Option[List[Comment]]],
      (json \ "checkin_comment").as[String],
      (json \ "rating_score").as[Int]*10,
      (json \ "created_at").as[String]
    ))

    def writes (c: Checkin): JsValue = Json.obj(
      "id" -> c.id,
      "user" -> c.user,
      "beer" -> c.beer,
      "brewery" -> c.brewery,
      "toasts" -> c.toasts,
      "comments" -> c.comments,
      "user_comment" -> c.user_comment,
      "rating" -> c.rating,
      "created_at" -> c.created_at
    )
  }
}



