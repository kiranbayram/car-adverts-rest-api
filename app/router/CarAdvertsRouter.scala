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
import repository._
import scala.collection._
import scala.concurrent.Future

object CarAdvertsRouter extends MongoCarAdvertsRepository with CarAdvertsRouter {
  def apply(): Router.Routes = routes
}

trait CarAdvertsRouter {

  self: CarAdvertsRepository =>

  val UnprocessableEntityStatusCode = 422

  def routes: Router.Routes = {

    case GET(p"/car/adverts") => Action.async { implicit request =>
      Future { 
        val sortingParam = request.queryString.get("sortby").flatMap(_.headOption).getOrElse("id")
        println(s"sortingParam: $sortingParam")
        
        val sortedAdverts = CarAdvert.sort(findAll, sortingParam)

        Ok(Json.toJson(sortedAdverts))
      }
    }

    case GET(p"/car/adverts/${long(id)}") => Action.async {
      Future {
        find(id) match {
          case Some(carAdvert) => Ok(Json.toJson(carAdvert))
          case None => NotFound
        }
      }
    }

    case POST(p"/car/adverts") => Action.async(parse.json[CarAdvert]) { implicit request =>
      val carAdvert = request.body
      val validationErrors = CarAdvertValidator.validate(carAdvert)

      if (validationErrors.isEmpty) {
        create(carAdvert)
        Future(Created)
      }
      else
        Future {
          Status(UnprocessableEntityStatusCode)(validationFailed(validationErrors))
        }
    }

    case PUT(p"/car/adverts/${long(id)}") => Action.async(parse.json[CarAdvert]) { implicit request =>
      val carAdvert = request.body
      val validationErrors = CarAdvertValidator.validate(carAdvert)

      if (validationErrors.isEmpty) {
        update(id, carAdvert)
        Future(NoContent)
      }
      else
        Future {
          Status(UnprocessableEntityStatusCode)(validationFailed(validationErrors))
        }
    }

    case DELETE(p"/car/adverts/${long(id)}") => Action.async {
      delete(id)
      Future(NoContent)
    }

  }

  private def validationFailed(errors: immutable.Set[String]): JsValue =
    JsObject(Seq("validation_errors" -> Json.toJson(errors)))

}

