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

  def findAll: List[CarAdvert] = 
    db.carAdvertsCollection.find().flatMap(_.asCarAdvert).toList

  def find(id: Long): Option[CarAdvert] = {
    val query = queryById(id)
    
    db.carAdvertsCollection.findOne(query).flatMap(_.asCarAdvert)
  }

  def create(carAdvert: CarAdvert): Boolean = 
    db.carAdvertsCollection.insert(carAdvert.asDBObject, WriteConcern.Safe).wasAcknowledged()

  def update(id: Long, carAdvert: CarAdvert): Boolean = {
    val query = queryById(id)

    db.carAdvertsCollection.update(query, carAdvert.asDBObject).wasAcknowledged()
  }

  def delete(id: Long): Unit = {
    val query = queryById(id)

    db.carAdvertsCollection.findAndRemove(query)
  }

  private def queryById(id: Long) = {
    MongoDBObject("id" -> id)
  }

}

