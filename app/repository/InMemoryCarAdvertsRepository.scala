package repository

import model.CarAdvert
import scala.collection.mutable

class InMemoryCarAdvertsRepository extends CarAdvertsRepository {

  val carAdverts = mutable.HashMap[Int, CarAdvert]()
  def carAdvertIds = carAdverts.values.map(_.id).toList

  def findAll(): List[CarAdvert] = carAdverts.values.toList

  def find(id: Int): Option[CarAdvert] = carAdverts.get(id)

  def create(carAdvert: CarAdvert): Boolean = {
    carAdverts.put(carAdvert.id, carAdvert)
    true
  }

  def update(id: Int, carAdvert: CarAdvert): Boolean = 
    if (carAdvertIds.contains(id)) {
      carAdverts.put(carAdvert.id, carAdvert)
      true
    }
    else false

  def delete(id: Int): Boolean = 
    if (carAdvertIds.contains(id)) {
      carAdverts.remove(id)
      true
    }
    else false
}
