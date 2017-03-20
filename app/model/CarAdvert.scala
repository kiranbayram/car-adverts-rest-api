package model

import org.joda.time.LocalDate
import play.api.libs.json.Json

case class CarAdvert (
	id: Int,
	title: String,
	fuelType: FuelTypes.Value,
	price: Int,
	isNew: Boolean,
	mileage: Option[Int] = None,	// Must be set only for used cars
	firstRegistration: Option[LocalDate] = None // Must be set only for used cars
)

object CarAdvert {
    implicit val carAdvertFormat = Json.format[CarAdvert]
	implicit val dateOrdering: Ordering[LocalDate] = Ordering.fromLessThan(_ isBefore _)

	def sort(adverts: Seq[CarAdvert], sortingQueryParam: String): Seq[CarAdvert] =
		sortingQueryParam.toLowerCase match {
			case "id" => adverts.sortBy(_.id)
			case "title" => adverts.sortBy(_.title)
			case "fueltype" => adverts.sortBy(_.fuelType)
			case "price" => adverts.sortBy(_.price)
			case "isnew" => adverts.sortBy(_.isNew)
			case "mileage" => adverts.sortBy(_.mileage)
			case "firstregistration" => adverts.sortBy(_.firstRegistration)
			case _ => adverts.sortBy(_.id)
		}
}
