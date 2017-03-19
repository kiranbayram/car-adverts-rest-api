package repository

import com.mongodb.casbah.WriteConcern
import com.mongodb.casbah.commons.MongoDBObject
import com.mongodb.DBObject
import DBObjectImplicits._
import model._
import org.bson.types.ObjectId
import play.api.libs.concurrent.Execution.Implicits._
import scala.concurrent.Future
import scala.util.Try

class MongoCarAdvertsRepository extends CarAdvertsRepository {
  lazy val db = CarAdvertsDB

class DomainWrapper(rawObject: DBObject) {
    def asCarAdvert: Option[CarAdvert] = {
      Try {
        val id = rawObject.asLong("id")
        val title = rawObject.asString("title")
        val price = rawObject.asLong("price")
        val fuelType: FuelTypes.Value = FuelTypes.withName(rawObject.asString("fuelType"))
        val isNew = rawObject.asBoolean("isNew")
        val mileage = Option(rawObject.asLong("mileage"))
        val firstRegistration = Option(rawObject.asLocalDate("firstRegistration"))

        Some(
          CarAdvert(id, title, fuelType, price, isNew, mileage, firstRegistration)
        )
      }.getOrElse(None)
    }
  }

  implicit def convertToDomainWrapper(rawObject: DBObject) = new DomainWrapper(rawObject)

  def fetchAll: Future[List[CarAdvert]] = Future {
    db.carAdvertsCollection.find().flatMap(_.asCarAdvert).toList
  }

  def find(id: Long): Future[Option[CarAdvert]] = ???

  def save(carAdvert: CarAdvert): Future[Unit] = ???

  def modify(id: Long, carAdvert: CarAdvert): Future[Unit] = ???

  def delete(id: Long): Future[Unit] = ???

  private def queryById(id: ObjectId) = {
    MongoDBObject("id" -> id)
  }

}

