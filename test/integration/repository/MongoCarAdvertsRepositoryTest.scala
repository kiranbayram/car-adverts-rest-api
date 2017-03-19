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

  test("Insert a carAdvert to database") {
    val carAdvert = CarAdvertExamples.newCar

    mongoRepo.create(carAdvert)

    val future = mongoRepo.find(carAdvert.id)

    future map { advert =>
      assert(advert == carAdvert)
    }

  }

  override def beforeEach = {
    collection.dropCollection()
  }
}
