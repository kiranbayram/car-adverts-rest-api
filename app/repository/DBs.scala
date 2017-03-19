package repository

import com.google.inject.Singleton
import com.mongodb.casbah.{MongoClient, MongoDB}

trait DB {
  val dbName: String
  lazy val carAdvertsCollection = mongoDB("caradverts")

  private lazy val mongoDB: MongoDB = mongoClient(dbName)
  private lazy val mongoClient: MongoClient = MongoClient()

  def apply(collName: String) = mongoDB(collName)
}

object CarAdvertsDB extends DB {
	val dbName: String = "car-adverts-db"
}

// Using a separate test db to avoid polluting prod db with test data.
object TestDB extends DB {
	val dbName: String = "test-db"
}
