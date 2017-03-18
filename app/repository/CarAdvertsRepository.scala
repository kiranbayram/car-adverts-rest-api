package repository

import model.CarAdvert
import play.api.libs.concurrent.Execution.Implicits._
import scala.collection.mutable
import scala.concurrent.Future

trait CarAdvertsRepository {

  def fetchAll: Future[List[CarAdvert]]

  def find(id: Long): Future[Option[CarAdvert]]

  def save(carAdvert: CarAdvert): Future[Unit]

  def modify(id: Long, carAdvert: CarAdvert): Future[Unit]

  def delete(id: Long): Future[Unit]

}

class DefaultCarAdvertsRepository extends CarAdvertsRepository {

  val carAdverts = mutable.HashMap[Long, CarAdvert]()

  def fetchAll: Future[List[CarAdvert]] = Future {
  	carAdverts.values.toList
  }

  def find(id: Long): Future[Option[CarAdvert]] = Future {
    carAdverts.get(id)
  }

  def save(carAdvert: CarAdvert): Future[Unit] = Future {
    carAdverts.put(carAdvert.id, carAdvert)
  }

  def modify(id: Long, carAdvert: CarAdvert): Future[Unit] = Future {
    carAdverts.put(id, carAdvert)
  }

  def delete(id: Long): Future[Unit] = Future {
  	carAdverts.remove(id)
  }

}
