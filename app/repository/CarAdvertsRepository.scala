package repository

import model.CarAdvert

trait CarAdvertsRepository {

  def findAll(): List[CarAdvert]

  def find(id: Long): Option[CarAdvert]

  def create(carAdvert: CarAdvert): Boolean

  def update(id: Long, carAdvert: CarAdvert): Boolean

  def delete(id: Long): Unit

}
