package router

import model.CarAdvert
import model.CarAdvertValidator
import model.Implicits._
import play.api.mvc.BodyParsers.parse
import play.api.libs.concurrent.Execution.Implicits._
import play.api.libs.json._
import play.api.mvc.Action
import play.api.mvc.Results._
import play.api.routing.Router
import play.api.routing.sird._
import repository.{DefaultCarAdvertsRepository, CarAdvertsRepository}
import scala.concurrent.Future

object CarAdvertsRouter extends DefaultCarAdvertsRepository with CarAdvertsRouter {
  def apply(): Router.Routes = routes
}

trait CarAdvertsRouter {

  self: CarAdvertsRepository =>

  def routes: Router.Routes = {

    case GET(p"/car/adverts") => Action.async { implicit request =>
      findAll.map { adverts => 
        val sortingParam = request.queryString.get("sortby").flatMap(_.headOption).getOrElse("id")

        println(s"sortingParam: $sortingParam")

        val sortedAdverts = CarAdvert.sort(adverts, sortingParam)

        Ok(Json.toJson(sortedAdverts))
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
      val validationErrors = CarAdvertValidator.validate(carAdvert)

      if (validationErrors.isEmpty)
        create(carAdvert) map (_ => Created)
      else
        Future(BadRequest(Json.toJson(validationErrors)))
    }

    case PUT(p"/car/adverts/${long(id)}") => Action.async(parse.json[CarAdvert]) { implicit request =>
      val carAdvert = request.body
      val validationErrors = CarAdvertValidator.validate(carAdvert)

      if (validationErrors.isEmpty)
        update(id, carAdvert) map (_ => NoContent)
      else
        Future(BadRequest(Json.toJson(validationErrors)))
    }

    case DELETE(p"/car/adverts/${long(id)}") => Action.async {
      delete(id) map (_ => NoContent)
    }

  }

}

