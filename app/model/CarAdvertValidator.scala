package model

import scala.collection._

object CarAdvertValidator {
	val priceCannotBeNegative = "Price cannot be negative"
	val idMustBePositive = "Id must be a positive number"
	val mileageMustBeSet = "Mileage must be set for used cars"
	val mileageMustBeEmpty = "Mileage must be empty for new cars"
	val mileageCannotBeNegative = "Mileage cannot be negative"
	val firstRegCannotBeInFuture = "First registration date cannot be in the future"
	val firstRegMustBeSet = "First registration date must be set for used cars"
	val firstRegMustBeEmpty = "First registration date must be empty for new cars"

	def validate(carAdvert: CarAdvert): Set[String] = {
		var errors: immutable.HashSet[String] = immutable.HashSet.empty

		if (carAdvert.id <= 0) 
			errors = errors + idMustBePositive
		if (carAdvert.price < 0) 
			errors = errors + priceCannotBeNegative

		if (carAdvert.isNew) {
			if (carAdvert.mileage.nonEmpty) 
				errors = errors + mileageMustBeEmpty
			if (carAdvert.firstRegistration.nonEmpty)
				errors = errors + firstRegMustBeEmpty
		}
		else {	//Used car
			if (carAdvert.mileage.isEmpty) 
				errors = errors + mileageMustBeSet
			else {
				if(carAdvert.mileage.get < 0)
					errors = errors + mileageCannotBeNegative
			}
			if (carAdvert.firstRegistration.isEmpty)
				errors = errors + firstRegMustBeSet
			else {
				if(carAdvert.firstRegistration.get.toDateTimeAtStartOfDay.isAfterNow)
					errors = errors + firstRegCannotBeInFuture
			}
		}

		errors
	}
}
