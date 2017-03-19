package repository

import com.google.inject.Singleton
import com.mongodb.casbah.{MongoClient, MongoDB}

object CarAdvertsDB {
  val dbName: String = "car-adverts-db"
  lazy val carAdvertsCollection = mongoDB("caradverts")

  private lazy val mongoDB: MongoDB = mongoClient(dbName)
  private lazy val mongoClient: MongoClient = MongoClient()

  def apply(collName: String) = mongoDB(collName)
}
