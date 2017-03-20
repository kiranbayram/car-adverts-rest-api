package repository

import com.mongodb.DBObject
import com.mongodb.casbah.commons.MongoDBObject
import com.mongodb.casbah.commons.conversions.scala._
import DBObjectImplicits._
import model._
import org.joda.time.DateTime
import scala.util.Try

object MongoConversionHelpers {

  RegisterJodaTimeConversionHelpers()
  RegisterJodaLocalDateTimeConversionHelpers

  class DomainWrapper(rawObject: DBObject) {
    def asCarAdvert: Option[CarAdvert] = {
      try {
        val id = rawObject.asLong("id")
        val title = rawObject.asString("title")
        val price = rawObject.asLong("price")
        val fuelType: FuelTypes.Value = FuelTypes.withName(rawObject.asString("fuelType"))
        val isNew = rawObject.asBoolean("isNew")
        val mileage = Try(Some(rawObject.asLong("mileage"))).getOrElse(None)
        val firstRegistration = Try(Some(rawObject.asLocalDate("firstRegistration"))).getOrElse(None)

        Some(
          CarAdvert(id, title, fuelType, price, isNew, mileage, firstRegistration)
        )}
      catch {
        case e: Exception => println(e.printStackTrace())
        None
      }
    }
  }

  class MongoCarAdvert(carAdvert: CarAdvert) {

    def asDBObject: DBObject = {
      val dbObject = MongoDBObject(
        "id" -> carAdvert.id,
        "title" -> carAdvert.title,
        "fuelType" -> carAdvert.fuelType.toString,
        "price" -> carAdvert.price,
        "isNew" -> carAdvert.isNew
      )

      carAdvert.mileage.foreach { mileage =>
        dbObject.put("mileage", mileage)
      }

      carAdvert.firstRegistration.foreach { firstRegistration =>
        dbObject.put("firstRegistration", firstRegistration.toDateTimeAtStartOfDay)
      }

      dbObject
    }
  }

  implicit def convertToMongoCarAdvert(carAdvert: CarAdvert) = new MongoCarAdvert(carAdvert)
  implicit def convertToDomainWrapper(rawObject: DBObject) = new DomainWrapper(rawObject)
}

