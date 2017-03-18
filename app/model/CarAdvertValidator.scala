package model

import scala.collection._

object CarAdvertValidator {
	val priceMustBePositive = "Price must be a positive number"
	val idMustBePositive = "Id must be a positive number"
	val mileageMustBeSet = "Mileage must be set for used cars"
	val mileageMustBeEmpty = "Mileage must be empty for new cars"
	val firstRegMustBeSet = "First registration date must be set for used cars"
	val firstRegMustBeEmpty = "First registration date must be empty for new cars"

	def validate(carAdvert: CarAdvert): Set[String] = {
		var errors: immutable.HashSet[String] = immutable.HashSet.empty

		if (carAdvert.id <= 0) 
			errors = errors + idMustBePositive
		if (carAdvert.price <= 0) 
			errors = errors + priceMustBePositive

		if (carAdvert.isNew) {
			if (carAdvert.mileage.nonEmpty) 
				errors = errors + mileageMustBeEmpty
			if (carAdvert.firstRegistration.nonEmpty)
				errors = errors + firstRegMustBeEmpty
		}
		else {	//Used car
			if (carAdvert.mileage.isEmpty) 
				errors = errors + mileageMustBeSet
			if (carAdvert.firstRegistration.isEmpty)
				errors = errors + firstRegMustBeSet
		}

		errors
	}
}
