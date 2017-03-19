package model

import CarAdvert.dateOrdering
import org.scalatest.FunSuite
import router.CarAdvertExamples.ForSortingTest._

class CarAdvertSortingTest extends FunSuite {
	test("Sorting by id") {
		val result = CarAdvert.sort(allAdverts, "id")

		assert(result == allAdverts.sortBy(_.id))
	}

	test("Sorting by title") {
		val result = CarAdvert.sort(allAdverts, "title")

		assert(result == allAdverts.sortBy(_.title))
	}

	test("Sorting by price") {
		val result = CarAdvert.sort(allAdverts, "price")

		assert(result == allAdverts.sortBy(_.price))
	}

	test("Sorting by fuel type") {
		val result = CarAdvert.sort(allAdverts, "fueltype")

		assert(result == allAdverts.sortBy(_.fuelType))
	}

	test("Sorting by isNew flag") {
		val result = CarAdvert.sort(allAdverts, "isnew")

		assert(result == allAdverts.sortBy(_.isNew))
	}

	test("Sorting by mileage") {
		val result = CarAdvert.sort(allAdverts, "mileage")

		assert(result == allAdverts.sortBy(_.mileage))
	}

	test("Sorting by first registration date") {
		val result = CarAdvert.sort(allAdverts, "firstregistration")

		assert(result == allAdverts.sortBy(_.firstRegistration))
	}

	test("Default sorting must be by id") {
		val result = CarAdvert.sort(allAdverts, "invalid-field")

		assert(result == allAdverts.sortBy(_.id))
	}

	test("Sorting must ignore case") {
		val result = CarAdvert.sort(allAdverts, "TITLE")

		assert(result == allAdverts.sortBy(_.title))
	}
}
