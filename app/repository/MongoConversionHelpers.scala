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


  class MongoCarAdvert(carAdvert: CarAdvert) {

    def asDBObject: DBObject =
      MongoDBObject(
        "id" -> carAdvert.id,
        "title" -> carAdvert.title,
        "fuelType" -> carAdvert.fuelType,
        "price" -> carAdvert.price,
        "isNew" -> carAdvert.isNew,
        "mileage" -> carAdvert.mileage,
        "firstRegistration" -> carAdvert.firstRegistration
      )
  }

  implicit def convertToMongoCarAdvert(carAdvert: CarAdvert) = new MongoCarAdvert(carAdvert)
  implicit def convertToDomainWrapper(rawObject: DBObject) = new DomainWrapper(rawObject)
}

