package controllers

import play.api.mvc.{Controller, Action}

object Hootsuite  extends Controller {
  def callback(uid: String, timezone: String, lang: String, isSsl: Int, pid: String, theme: String) = Action {
    
    Redirect("/ui/stream")
  }
}