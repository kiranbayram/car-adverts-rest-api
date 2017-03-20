package repository

import model.CarAdvert
import scala.collection.mutable


class InMemoryCarAdvertsRepository extends CarAdvertsRepository {

  val carAdverts = mutable.HashMap[Long, CarAdvert]()

  def findAll(): List[CarAdvert] = carAdverts.values.toList

  def find(id: Long): Option[CarAdvert] = carAdverts.get(id)

  def create(carAdvert: CarAdvert): Boolean = {
    carAdverts.put(carAdvert.id, carAdvert)

    true
  }

  def update(id: Long, carAdvert: CarAdvert): Boolean = {
    carAdverts.put(carAdvert.id, carAdvert)

    true
  }

  def delete(id: Long): Unit = carAdverts.remove(id)

}
