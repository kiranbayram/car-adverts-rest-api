package router

import org.joda.time.LocalDate
import model._

object CarAdvertExamples {
	val newCar =
		CarAdvert (
			id = 1,
			title = "Simple new car advert",
			fuelType = FuelTypes.Diesel,
			price = 20000,
			isNew = true
		)

	val usedCar =
		CarAdvert (
			id = 2,
			title = "Simple advert for a used car",
			fuelType = FuelTypes.Gasoline,
			price = 8999,
			isNew = false,
			mileage = Some(4567),
			firstRegistration = Some(new LocalDate(2016, 5, 5))
		)
}
