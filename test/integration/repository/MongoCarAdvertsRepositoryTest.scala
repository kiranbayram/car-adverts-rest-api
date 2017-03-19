package repository

import com.mongodb.casbah.WriteConcern
import model._
import org.bson.types.ObjectId
import org.joda.time.{DateTime, LocalDate}
import org.scalatest.{BeforeAndAfterEach, FunSuite}
import play.api.libs.concurrent.Execution.Implicits._
import router.CarAdvertExamples

class MongoCarAdvertsRepositoryTest extends FunSuite with BeforeAndAfterEach {

  val mongoRepo: MongoCarAdvertsRepository = new MongoCarAdvertsRepository(TestDB)
  val collection = TestDB.carAdvertsCollection

  test("Insert a car advert for a new car to database") {
    val carAdvert = CarAdvertExamples.newCar

    mongoRepo.create(carAdvert)

    val result = mongoRepo.find(carAdvert.id)

    assert(result == Some(carAdvert))
  }

  test("Insert a car advert for a used car to database") {
    val carAdvert = CarAdvertExamples.usedCar

    mongoRepo.create(carAdvert)

    val result = mongoRepo.find(carAdvert.id)

    assert(result == Some(carAdvert))
  }

  override def beforeEach = {
    collection.dropCollection()
  }
}
