package model

import play.api.libs.json._

object FuelTypes extends Enumeration {

	val Gasoline = Value("gasoline")
	val Diesel = Value("diesel")


	implicit val fuelTypeReads = Reads.enumNameReads(FuelTypes)
}
