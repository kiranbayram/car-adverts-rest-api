package repository

import com.mongodb.casbah.WriteConcern
import model._
import MongoConversionHelpers._
import org.scalatest.{BeforeAndAfterEach, FunSuite}
import play.api.libs.concurrent.Execution.Implicits._
import router.CarAdvertExamples

class MongoCarAdvertsRepositoryTest extends FunSuite with BeforeAndAfterEach {

  val mongoRepo: MongoCarAdvertsRepository = new MongoCarAdvertsRepository(TestDB)
  val collection = TestDB.carAdvertsCollection

  test("Fetch a car advert from database") {
    val carAdvert = CarAdvertExamples.newCar

    assert(collection.insert(carAdvert.asDBObject, WriteConcern.Safe).wasAcknowledged())

    val fetched = mongoRepo.find(carAdvert.id).get

    assert(fetched == carAdvert)
  }

  test("Fetch all car adverts from database") {
    val newCar = CarAdvertExamples.newCar
    val usedCar = CarAdvertExamples.usedCar

    assert(collection.insert(newCar.asDBObject, WriteConcern.Safe).wasAcknowledged())
    assert(collection.insert(usedCar.asDBObject, WriteConcern.Safe).wasAcknowledged())

    val fetched = mongoRepo.findAll().toSet

    assert(fetched == Set(newCar, usedCar))
  }

  test("Insert a car advert for a new car to database") {
    val carAdvert = CarAdvertExamples.newCar

    assert(mongoRepo.create(carAdvert))

    val result = mongoRepo.find(carAdvert.id)

    assert(result == Some(carAdvert))
  }

  test("Insert a car advert for a used car to database") {
    val carAdvert = CarAdvertExamples.usedCar

    assert(mongoRepo.create(carAdvert))

    val result = mongoRepo.find(carAdvert.id)

    assert(result == Some(carAdvert))
  }

  test("Update a car advert in database") {
    val carAdvert = CarAdvertExamples.newCar
    assert(mongoRepo.create(carAdvert))

    val updatedAdvert = carAdvert.copy(title = "Updated title")
    assert(mongoRepo.update(carAdvert.id, updatedAdvert))

    val result = mongoRepo.find(carAdvert.id)

    assert(result == Some(updatedAdvert))
  }

  test("Delete a car advert from database") {
    val carAdvert = CarAdvertExamples.newCar
    assert(mongoRepo.create(carAdvert))

    mongoRepo.delete(carAdvert.id)

    val result = mongoRepo.find(carAdvert.id)

    assert(result == None)
  }

  override def beforeEach = {
    collection.dropCollection()
  }
}
