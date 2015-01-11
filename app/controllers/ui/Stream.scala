package controllers.ui

import play.api.mvc.{Controller, Action}
import play.api.Play.current


object Stream extends Controller {
  def default = Action { request =>
    Ok(views.html.stream())
  }
}
