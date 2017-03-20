package router

import controller.CarAdvertController
import model.CarAdvert
import play.api._
import play.api.ApplicationLoader.Context
import play.api.libs.json._
import play.api.routing.Router
import play.api.test._
import repository._

class CarAdvertsRouterSpec extends PlaySpecification {

  object FakeCarAdvertsRouter extends CarAdvertsRouter {
    def apply(): Router.Routes = 
      routes(new CarAdvertController(new InMemoryCarAdvertsRepository))
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

    "Not find non-existing CarAdvert" in new WithApplicationLoader(fakeAppLoader) {
      val fakeRequest = FakeRequest(GET, "/car/adverts/999")
      val Some(result) = route(fakeRequest)

      status(result) must equalTo(NOT_FOUND)
    }

    "Create and find the CarAdvert for a new car" in new WithApplicationLoader(fakeAppLoader) {
      val body = Json.toJson(CarAdvertExamples.newCar)
      val fakePost = FakeRequest(POST, "/car/adverts").withJsonBody(body)
      val Some(postResult) = route(fakePost)

      status(postResult) must equalTo(CREATED)

      contentType(postResult) must beSome("application/json")
      contentAsJson(postResult) must equalTo(body)
    }

    "Create and find the CarAdvert for a used car" in new WithApplicationLoader(fakeAppLoader) {
      val body = Json.toJson(CarAdvertExamples.usedCar)
      val fakePost = FakeRequest(POST, "/car/adverts").withJsonBody(body)
      val Some(postResult) = route(fakePost)

      status(postResult) must equalTo(CREATED)

      contentType(postResult) must beSome("application/json")
      contentAsJson(postResult) must equalTo(body)
    }

    "Fetch all CarAdverts" in new WithApplicationLoader(fakeAppLoader) {
      val body = Json.toJson(CarAdvertExamples.newCar)
      val fakePost = FakeRequest(POST, "/car/adverts").withJsonBody(body)
      val Some(postResult) = route(fakePost)

      status(postResult) must equalTo(CREATED)

      val bodyForUsedCar = Json.toJson(CarAdvertExamples.usedCar)
      val fakePostForUsedCar = FakeRequest(POST, "/car/adverts").withJsonBody(bodyForUsedCar)
      val Some(postResultForUsedCar) = route(fakePostForUsedCar)

      status(postResult) must equalTo(CREATED)

      val fakeRequest = FakeRequest(GET, s"/car/adverts")
      val Some(result) = route(fakeRequest)

      status(result) must equalTo(OK)
      contentType(result) must beSome("application/json")
      contentAsJson(result) must equalTo(JsArray(Seq(body, bodyForUsedCar)))
    }

    "Modify a CarAdvert" in new WithApplicationLoader(fakeAppLoader) {
      val body = Json.toJson(CarAdvertExamples.newCar)
      val fakePost = FakeRequest(POST, "/car/adverts").withJsonBody(body)
      val Some(postResult) = route(fakePost)

      status(postResult) must equalTo(CREATED)

      val updated = CarAdvertExamples.newCar.copy(title = "Updated title")
      val updateBody = Json.toJson(updated)

      val fakePut = FakeRequest(PUT, s"/car/adverts/${CarAdvertExamples.newCar.id}").withJsonBody(updateBody)
      val Some(putResult) = route(fakePut)

      status(putResult) must equalTo(OK)

      contentType(putResult) must beSome("application/json")
      contentAsJson(putResult) must equalTo(updateBody)
    }

    "Delete a CarAdvert" in new WithApplicationLoader(fakeAppLoader) {
      val body = Json.toJson(CarAdvertExamples.newCar)
      val fakePost = FakeRequest(POST, "/car/adverts").withJsonBody(body)
      val Some(postResult) = route(fakePost)

      status(postResult) must equalTo(CREATED)

      val fakeDelete = FakeRequest(DELETE, s"/car/adverts/${CarAdvertExamples.newCar.id}")
      val Some(deleteResult) = route(fakeDelete)

      status(deleteResult) must equalTo(NO_CONTENT)

      val fakeRequest = FakeRequest(GET, s"/car/adverts/${CarAdvertExamples.newCar.id}")
      val Some(result) = route(fakeRequest)

      status(result) must equalTo(NOT_FOUND)
    }

    "Return 404 to a put request for a non-existing advert" in new WithApplicationLoader(fakeAppLoader) {
      val updated = CarAdvertExamples.newCar.copy(title = "Updated title")
      val updateBody = Json.toJson(updated)

      val fakePut = FakeRequest(PUT, s"/car/adverts/999").withJsonBody(updateBody)
      val Some(putResult) = route(fakePut)

      status(putResult) must equalTo(NOT_FOUND)
    }

    "Return 404 to a delete request for a non-existing advert" in new WithApplicationLoader(fakeAppLoader) {
      val fakeDelete = FakeRequest(DELETE, s"/car/adverts/999")
      val Some(deleteResult) = route(fakeDelete)

      status(deleteResult) must equalTo(NOT_FOUND)
    }
  }

}
