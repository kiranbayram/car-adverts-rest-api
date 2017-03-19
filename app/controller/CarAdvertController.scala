package controller

import model._
import model.Implicits._
import play.api.libs.concurrent.Execution.Implicits._
import play.api.libs.json._
import play.api.mvc.{Action, Controller}
import repository._
import scala.collection._
import scala.concurrent.Future

class CarAdvertController(repository: CarAdvertsRepository = new MongoCarAdvertsRepository) extends Controller {
  val UnprocessableEntityStatusCode = 422

	def viewAll = Action.async { implicit request =>
      Future { 
        val sortingParam = request.queryString.get("sortby").flatMap(_.headOption).getOrElse("id")
        println(s"sortingParam: $sortingParam")
        
        val sortedAdverts = CarAdvert.sort(repository.findAll, sortingParam)

        Ok(Json.toJson(sortedAdverts))
      }
    }

    def view(id: Long) = Action.async {
      Future {
        repository.find(id) match {
          case Some(carAdvert) => Ok(Json.toJson(carAdvert))
          case None => NotFound
        }
      }
    }

    def create = Action.async(parse.json[CarAdvert]) { implicit request =>
      val carAdvert = request.body
      val validationErrors = CarAdvertValidator.validate(carAdvert)

      if (validationErrors.isEmpty) {
        repository.create(carAdvert)
        Future(Created)
      }
      else
        Future {
          Status(UnprocessableEntityStatusCode)(validationFailed(validationErrors))
        }
    }

    def modify(id: Long) = Action.async(parse.json[CarAdvert]) { implicit request =>
      val carAdvert = request.body
      val validationErrors = CarAdvertValidator.validate(carAdvert)

      if (validationErrors.isEmpty) {
        repository.update(id, carAdvert)
        Future(NoContent)
      }
      else
        Future {
          Status(UnprocessableEntityStatusCode)(validationFailed(validationErrors))
        }
    }

    def delete(id: Long) = Action.async {
      repository.delete(id)
      Future(NoContent)
    }

  	private def validationFailed(errors: immutable.Set[String]): JsValue =
    	JsObject(Seq("validation_errors" -> Json.toJson(errors)))
}
