package model

import CarAdvertValidator._
import org.joda.time.LocalDate
import org.scalatest.{BeforeAndAfterEach, FunSuite}

class CarAdvertValidationTest extends FunSuite {
	
	test("Id must be positive") {
	    val carAdvert = 
	    	CarAdvert(
	    		id = -1,
	    		title = "Title",
				fuelType = FuelTypes.Diesel,
				price = 345,
				isNew = true
	    	)

	    val errors = validate(carAdvert)
	    assert(errors == Set(idMustBePositive))
  	}

  	test("Price cannot be negative") {
	    val carAdvert = 
	    	CarAdvert(
	    		id = 1,
	    		title = "Title",
				fuelType = FuelTypes.Diesel,
				price = -1,
				isNew = true
	    	)

	    val errors = validate(carAdvert)
	    assert(errors == Set(priceCannotBeNegative))
  	}

  	test("Mileage cannot be negative") {
	    val carAdvert = 
	    	CarAdvert(
	    		id = 1,
	    		title = "Title",
				fuelType = FuelTypes.Diesel,
				price = 999,
				isNew = false,
				mileage = Some(-1),
				firstRegistration = Some(new LocalDate("2016-05-05"))
	    	)

	    val errors = validate(carAdvert)
	    assert(errors == Set(mileageCannotBeNegative))
  	}

  	test("First registration date cannot be in the future") {
	    val carAdvert = 
	    	CarAdvert(
	    		id = 1,
	    		title = "Title",
				fuelType = FuelTypes.Diesel,
				price = 999,
				isNew = false,
				mileage = Some(99),
				firstRegistration = Some(new LocalDate("2119-05-05"))
	    	)

	    val errors = validate(carAdvert)
	    assert(errors == Set(firstRegCannotBeInFuture))
  	}

  	test("Mileage must be set for used cars") {
	    val carAdvert = 
	    	CarAdvert(
	    		id = 1,
	    		title = "Title",
				fuelType = FuelTypes.Diesel,
				price = 999,
				isNew = false,
				mileage = None,
				firstRegistration = Some(new LocalDate("2016-05-05"))
	    	)

	    val errors = validate(carAdvert)
	    assert(errors == Set(mileageMustBeSet))
  	}

  	test("First registration date must be set for used cars") {
	    val carAdvert = 
	    	CarAdvert(
	    		id = 1,
	    		title = "Title",
				fuelType = FuelTypes.Diesel,
				price = 999,
				isNew = false,
				mileage = Some(44),
				firstRegistration = None
	    	)

	    val errors = validate(carAdvert)
	    assert(errors == Set(firstRegMustBeSet))
  	}

  	test("Mileage must be empty for new cars") {
	    val carAdvert = 
	    	CarAdvert(
	    		id = 1,
	    		title = "Title",
				fuelType = FuelTypes.Diesel,
				price = 999,
				isNew = true,
				mileage = Some(44),
				firstRegistration = None
	    	)

	    val errors = validate(carAdvert)
	    assert(errors == Set(mileageMustBeEmpty))
  	}

  	test("First registration date must be empty for new cars") {
	    val carAdvert = 
	    	CarAdvert(
	    		id = 1,
	    		title = "Title",
				fuelType = FuelTypes.Diesel,
				price = 999,
				isNew = true,
				mileage = None,
				firstRegistration = Some(new LocalDate("2016-05-05"))
	    	)

	    val errors = validate(carAdvert)
	    assert(errors == Set(firstRegMustBeEmpty))
  	}

  	test("Multiple errors should be returned when necessary") {
	    val carAdvert = 
	    	CarAdvert(
	    		id = -1,
	    		title = "Title",
				fuelType = FuelTypes.Diesel,
				price = -1,
				isNew = true
	    	)

	    val errors = validate(carAdvert)
	    assert(errors == Set(idMustBePositive, priceCannotBeNegative))
  	}
}
