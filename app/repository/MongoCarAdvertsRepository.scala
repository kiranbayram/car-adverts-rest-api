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

class MongoCarAdvertsRepository(db: DB = CarAdvertsDB) extends CarAdvertsRepository {

  def findAll(): List[CarAdvert] = 
    db.carAdvertsCollection.find().flatMap(_.asCarAdvert).toList

  def find(id: Int): Option[CarAdvert] = {
    val query = queryById(id)

    db.carAdvertsCollection.findOne(query).flatMap(_.asCarAdvert)
  }

  def create(carAdvert: CarAdvert): Boolean = 
    db.carAdvertsCollection.insert(carAdvert.asDBObject, WriteConcern.Safe).wasAcknowledged()

  def update(id: Int, carAdvert: CarAdvert): Boolean = {
    val query = queryById(id)

    if (db.carAdvertsCollection.findOne(query).nonEmpty) {
      db.carAdvertsCollection.update(query, carAdvert.asDBObject)
      true
    }
    else false
  }

  def delete(id: Int): Boolean = {
    val query = queryById(id)

    db.carAdvertsCollection.findAndRemove(query).nonEmpty
  }

  private def queryById(id: Int) = {
    MongoDBObject("id" -> id)
  }

}

