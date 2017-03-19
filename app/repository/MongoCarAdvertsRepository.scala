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

  def findAll: Future[List[CarAdvert]] = Future {
    db.carAdvertsCollection.find().flatMap(_.asCarAdvert).toList
  }

  def find(id: Long): Future[Option[CarAdvert]] = Future {
    val query = queryById(id)

    db.carAdvertsCollection.findOne(query).flatMap(_.asCarAdvert)
  }

  def create(carAdvert: CarAdvert): Future[Unit] = Future {
    db.carAdvertsCollection.insert(carAdvert.asDBObject, WriteConcern.Safe)
  }

  def update(id: Long, carAdvert: CarAdvert): Future[Unit] = Future {
    val query = queryById(id)

    db.carAdvertsCollection.update(query, carAdvert.asDBObject)
  }

  def delete(id: Long): Future[Unit] = Future {
    val query = queryById(id)

    db.carAdvertsCollection.findAndRemove(query)
  }

  private def queryById(id: Long) = {
    MongoDBObject("id" -> id)
  }

}

