package router

import controller.CarAdvertController
import play.api.Logger
import play.api.routing.Router
import play.api.routing.sird._

object CarAdvertsRouter extends  CarAdvertsRouter {
  def apply(): Router.Routes = routes(new CarAdvertController)
}

trait CarAdvertsRouter {

  def routes(controller: CarAdvertController): Router.Routes = {
    
    case GET(p"/car/adverts") => {
    	Logger.debug("Received api call -> GET /car/adverts")

    	controller.viewAll
    }

    case GET(p"/car/adverts/${long(id)}") => {
    	Logger.debug(s"Received api call -> GET /car/adverts/$id")

    	controller.view(id)
    }

    case POST(p"/car/adverts") => {
    	Logger.debug(s"Received api call -> POST /car/adverts")

    	controller.create
    }

    case PUT(p"/car/adverts/${long(id)}") => {
    	Logger.debug(s"Received api call -> PUT /car/adverts/$id")

    	controller.modify(id)
    }

    case DELETE(p"/car/adverts/${long(id)}") => {
    	Logger.debug(s"Received api call -> GET /car/adverts/$id")

    	controller.delete(id)
    }

  }

}

