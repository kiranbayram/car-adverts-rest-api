package repository

import model.CarAdvert
import play.api.libs.concurrent.Execution.Implicits._
import scala.collection.mutable
import scala.concurrent.Future

trait CarAdvertsRepository {

  def findAll: List[CarAdvert]

  def find(id: Long): Option[CarAdvert]

  def create(carAdvert: CarAdvert): Boolean

  def update(id: Long, carAdvert: CarAdvert): Boolean

  def delete(id: Long): Unit

}

class DefaultCarAdvertsRepository extends CarAdvertsRepository {

  val carAdverts = mutable.HashMap[Long, CarAdvert]()

  def findAll: List[CarAdvert] = carAdverts.values.toList

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

