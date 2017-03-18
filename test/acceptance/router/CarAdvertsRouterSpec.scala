package router

import model.Implicits._
import model.CarAdvert
import play.api._
import play.api.ApplicationLoader.Context
import play.api.libs.json._
import play.api.routing.Router
import play.api.test._
import repository.DefaultCarAdvertsRepository

class CarAdvertsRouterSpec extends PlaySpecification {

  object FakeCarAdvertsRouter extends DefaultCarAdvertsRepository with CarAdvertsRouter {
    def apply(): Router.Routes = routes
  }

  val fakeAppLoader = new ApplicationLoader() {
    def load(context: Context): Application = new BuiltInComponentsFromContext(context) {
      def router = Router.from {
        FakeCarAdvertsRouter()
      }
    }.application
  }

  val fakeRouter = Router.from(FakeCarAdvertsRouter())

  "Car Adverts Router" should {

    "Have a specific handler to GET an CarAdvert by id" in new WithApplication() {
      val fakeRequest = FakeRequest(GET, "/car/adverts/1")
      val handler = fakeRouter.handlerFor(fakeRequest)

      handler must be_!=(None)
    }

    "Have a specific handler to CREATE an CarAdvert" in new WithApplication() {
      val body = CarAdvertExamples.newCar
      val fakeRequest = FakeRequest(POST, "/car/adverts").withBody(body)
      val handler = fakeRouter.handlerFor(fakeRequest)

      handler must be_!=(None)
    }

    "Have a specific handler to MODIFY an CarAdvert by id" in new WithApplication() {
      val body = CarAdvertExamples.newCar
      val fakeRequest = FakeRequest(PUT, "/car/adverts/1").withBody(body)
      val handler = fakeRouter.handlerFor(fakeRequest)

      handler must be_!=(None)
    }

    "Have a specific handler to DELETE an CarAdvert by id" in new WithApplication() {
      val fakeRequest = FakeRequest(DELETE, "/car/adverts/1")
      val handler = fakeRouter.handlerFor(fakeRequest)

      handler must be_!=(None)
    }

    "Have a specific handler to GET all CarAdverts" in new WithApplication() {
      val body = CarAdvertExamples.newCar
      val fakeRequest = FakeRequest(GET, "/car/adverts").withBody(body)
      val handler = fakeRouter.handlerFor(fakeRequest)

      handler must be_!=(None)
    }

    "Not find non-existing car advert" in new WithApplicationLoader(fakeAppLoader) {
      val fakeRequest = FakeRequest(GET, "/car/adverts/999")
      val Some(result) = route(fakeRequest)

      status(result) must equalTo(NOT_FOUND)
    }
  }

}
