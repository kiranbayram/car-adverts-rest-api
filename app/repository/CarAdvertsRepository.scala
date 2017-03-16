package repository

import model.CarAdvert
import play.api.libs.concurrent.Execution.Implicits._
import scala.collection.mutable
import scala.concurrent.Future

trait CarAdvertsRepository {

  def find(id: Long): Future[Option[CarAdvert]]

  def save(carAdvert: CarAdvert): Future[Unit]

}

class DefaultCarAdvertsRepository extends CarAdvertsRepository {

  val carAdverts = mutable.HashMap[Long, CarAdvert]()

  def find(id: Long): Future[Option[CarAdvert]] = Future {
    carAdverts.get(id)
  }

  def save(carAdvert: CarAdvert): Future[Unit] = Future {
    carAdverts.put(carAdvert.id, carAdvert)
  }

}
