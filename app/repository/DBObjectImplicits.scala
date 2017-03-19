package repository

import com.mongodb.casbah.Imports._
import org.joda.time.{LocalDate, DateTime}

object DBObjectImplicits {

  implicit def toDBObjectHelper(obj: DBObject) = new DBObjectHelper(obj)

}

class DBObjectHelper(underlying: DBObject) {

  def asObjectId(key: String) = underlying.as[ObjectId](key)

  def asBoolean(key: String) = underlying.as[Boolean](key)

  def asByteArray(key: String) = underlying.as[Array[Byte]](key)

  def asString(key: String) = underlying.as[String](key)

  def asDouble(key: String) = underlying.as[Double](key)

  def asInt(key: String) = underlying.as[Int](key)

  def asLong(key: String) = underlying.as[Long](key)

  def asList[A](key: String) =
    (List() ++ underlying(key).asInstanceOf[BasicDBList]) map { _.asInstanceOf[A] }

  def asDateTime(key: String) = underlying.as[DateTime](key)

  def asLocalDate(key: String) = new LocalDate(asDateTime(key))

  def asDoubleList(key: String) = asList[Double](key)
}
