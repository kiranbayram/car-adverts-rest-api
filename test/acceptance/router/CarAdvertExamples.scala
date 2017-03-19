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

	object ForSortingTest {
		val advert1 =
			CarAdvert (
				id = 1,
				title = "b title",
				fuelType = FuelTypes.Diesel,
				price = 500,
				isNew = true
			)

		val advert2 =
			CarAdvert (
				id = 3,
				title = "z title",
				fuelType = FuelTypes.Gasoline,
				price = 300,
				isNew = false,
				mileage = Some(5000),
				firstRegistration = Some(new LocalDate(2016, 5, 5))
			)

		val advert3 =
			CarAdvert (
				id = 2,
				title = "a title",
				fuelType = FuelTypes.Diesel,
				price = 600,
				isNew = false,
				mileage = Some(7000),
				firstRegistration = Some(new LocalDate(2017, 5, 5))
			)

		val advert4 =
			CarAdvert (
				id = 4,
				title = "r title",
				fuelType = FuelTypes.Gasoline,
				price = 50,
				isNew = false,
				mileage = Some(6000),
				firstRegistration = Some(new LocalDate(2015, 5, 5))
			)

		val allAdverts = List(advert1, advert2, advert3, advert4)
	}
}
