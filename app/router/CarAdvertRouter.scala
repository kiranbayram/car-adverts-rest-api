package router

import model.CarAdvert
import model.Implicits._
import play.api.mvc.BodyParsers.parse
import play.api.libs.concurrent.Execution.Implicits._
import play.api.libs.json._
import play.api.mvc.Action
import play.api.mvc.Results._
import play.api.routing.Router
import play.api.routing.sird._
import repository.{DefaultCarAdvertsRepository, CarAdvertsRepository}

object CarAdvertsRouter extends DefaultCarAdvertsRepository with CarAdvertsRouter {
  def apply(): Router.Routes = routes
}

trait CarAdvertsRouter {

  self: CarAdvertsRepository =>

  def routes: Router.Routes = {

    case GET(p"/car/adverts") => Action.async {
      fetchAll.map { adverts => 
        Ok(Json.toJson(adverts))
      }
    }

    case GET(p"/car/adverts/${long(id)}") => Action.async {
      find(id) map {
        case Some(carAdvert) => Ok(Json.toJson(carAdvert))
        case None => NotFound
      }
    }

    case POST(p"/car/adverts") => Action.async(parse.json[CarAdvert]) { implicit request =>
      val carAdvert = request.body
      save(carAdvert) map (_ => Created)
    }

    case PUT(p"/car/adverts/${long(id)}") => Action.async(parse.json[CarAdvert]) { implicit request =>
      val carAdvert = request.body
      modify(id, carAdvert) map (_ => Ok)
    }

  }

}

