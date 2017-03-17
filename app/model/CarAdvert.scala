package model

case class CarAdvert (
	id: Long,
	title: String,
	fuelType: FuelTypes.Value,
	price: Long,
	isNew: Boolean,
	mileage: Option[Long] = None//,
	//firstRegistration: Option[LocalDate]
)
