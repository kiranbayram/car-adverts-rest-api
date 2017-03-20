package repository

import model.CarAdvert

trait CarAdvertsRepository {

  def findAll(): List[CarAdvert]

  def find(id: Int): Option[CarAdvert]

  def create(carAdvert: CarAdvert): Boolean

  def update(id: Int, carAdvert: CarAdvert): Boolean

  def delete(id: Int): Boolean

}
