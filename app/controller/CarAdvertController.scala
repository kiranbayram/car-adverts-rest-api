package controller

import model._
import play.api.libs.concurrent.Execution.Implicits._
import play.api.libs.json._
import play.api.Logger
import play.api.mvc.{Action, Controller}
import repository._
import scala.collection._
import scala.concurrent.Future

class CarAdvertController(repository: CarAdvertsRepository = new MongoCarAdvertsRepository) extends Controller {
  val UnprocessableEntityStatusCode = 422

	def viewAll = Action.async { implicit request =>
      Future { 
        val sortingParam = request.queryString.get("sortby").flatMap(_.headOption).getOrElse("id")
        
        Logger.debug(s"Using sortingParam: $sortingParam")
        
        val sortedAdverts = CarAdvert.sort(repository.findAll, sortingParam)
        val advertsJson = Json.toJson(sortedAdverts)

        Logger.debug(s"Response with status code 200 (Ok) returned. Returned adverts: ${advertsJson}")
        Ok(advertsJson)
      }
    }

    def view(id: Int) = Action.async {
      Future {
        repository.find(id) match {
          case Some(carAdvert) => {
            val advertJson = Json.toJson(carAdvert)

            Logger.debug(s"Response with status code 200 (Ok) returned. Returned advert: ${advertJson}")
            Ok(advertJson)
          }
          case None => {
            Logger.debug(s"Response with status code 404 (NotFound) returned.")
            NotFound
          }
        }
      }
    }

    def create() = Action.async(parse.json[CarAdvert]) { implicit request =>
      val carAdvert = request.body
      val validationErrors = CarAdvertValidator.validate(carAdvert)

      if (validationErrors.isEmpty) {
        repository.create(carAdvert)

        Logger.debug(s"Response with status code 201 (Created) returned.")
        Future(Created(Json.toJson(carAdvert)).withHeaders(LOCATION -> s"/car/adverts/${carAdvert.id}"))
      }
      else
        Future {
          val errorsJson = validationFailed(validationErrors)

          Logger.debug(s"Response with status code 422 (UnprocessableEntity) returned. Errors: ${errorsJson}")
          Status(UnprocessableEntityStatusCode)(errorsJson)
        }
    }

    def modify(id: Int) = Action.async(parse.json[CarAdvert]) { implicit request =>
      val carAdvert = request.body
      val validationErrors = CarAdvertValidator.validate(carAdvert)

      if (validationErrors.isEmpty) {
        if(repository.update(id, carAdvert)) {
          Logger.debug(s"Response with status code 200 (Ok) returned.")
          Future(Ok(Json.toJson(carAdvert)))
        }
        else {
          Logger.debug(s"Response with status code 404 (NotFound) returned.")
          Future(NotFound)
        }
      }
      else
        Future {
          val errorsJson = validationFailed(validationErrors)

          Logger.debug(s"Response with status code 422 (UnprocessableEntity) returned. Errors: ${errorsJson}")
          Status(UnprocessableEntityStatusCode)(errorsJson)
        }
    }

    def delete(id: Int) = Action.async {
      if(repository.delete(id)) {
        Logger.debug(s"Response with status code 204 (NoContent) returned.")
        Future(NoContent)
      }
      else {
        Logger.debug(s"Response with status code 404 (NotFound) returned.")
        Future(NotFound)
      }

      
    }

  	private def validationFailed(errors: immutable.Set[String]): JsValue =
    	JsObject(Seq("validation_errors" -> Json.toJson(errors)))
}
