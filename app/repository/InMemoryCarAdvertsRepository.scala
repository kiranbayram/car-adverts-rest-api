package repository

import model.CarAdvert
import scala.collection.mutable

class InMemoryCarAdvertsRepository extends CarAdvertsRepository {

  val carAdverts = mutable.HashMap[Int, CarAdvert]()

  def findAll(): List[CarAdvert] = carAdverts.values.toList

  def find(id: Int): Option[CarAdvert] = carAdverts.get(id)

  def create(carAdvert: CarAdvert): Boolean = {
    carAdverts.put(carAdvert.id, carAdvert)
    true
  }

  def update(id: Int, carAdvert: CarAdvert): Boolean = {
    carAdverts.put(carAdvert.id, carAdvert)
    true
  }

  def delete(id: Int): Boolean = {
    carAdverts.remove(id)
    true
  }
}
