package repository

import com.mongodb.casbah.WriteConcern
import com.mongodb.casbah.commons.MongoDBObject
import com.mongodb.DBObject
import DBObjectImplicits._
import model._
import MongoConversionHelpers._
import org.bson.types.ObjectId
import play.api.libs.concurrent.Execution.Implicits._
import scala.concurrent.Future
import scala.util.Try

class MongoCarAdvertsRepository extends CarAdvertsRepository {
  lazy val db = CarAdvertsDB

  def fetchAll: Future[List[CarAdvert]] = Future {
    db.carAdvertsCollection.find().flatMap(_.asCarAdvert).toList
  }

  def find(id: Long): Future[Option[CarAdvert]] = ???

  def save(carAdvert: CarAdvert): Future[Unit] = ???

  def modify(id: Long, carAdvert: CarAdvert): Future[Unit] = ???

  def delete(id: Long): Future[Unit] = ???

  private def queryById(id: ObjectId) = {
    MongoDBObject("id" -> id)
  }

}

