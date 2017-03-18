package router

import model._

object CarAdvertExamples {
	val newCar =
		CarAdvert (
			id= 1,
			title= "Simple new car advert",
			fuelType= FuelTypes.Diesel,
			price= 20000,
			isNew= true
		)
}
