package mongodb

import com.typesafe.config.ConfigFactory
import org.bson.codecs.configuration.CodecRegistries._
import org.mongodb.scala._
import org.mongodb.scala.bson.codecs.DEFAULT_CODEC_REGISTRY
import org.mongodb.scala.bson.codecs.Macros._

import models._

object Mongo {
  lazy val config = ConfigFactory.load()
  lazy val mongoClient: MongoClient = MongoClient("mongodb://rentyadmin:rentyadmin123@ds037067.mlab.com:37067/renty")
  lazy val codecRegistry = fromRegistries(fromProviders(classOf[Car]), DEFAULT_CODEC_REGISTRY)
  lazy val database: MongoDatabase = mongoClient.getDatabase("renty").withCodecRegistry(codecRegistry)
  lazy val carCollection: MongoCollection[Car] = database.getCollection[Car]("cars")
  lazy val rentalCollection: MongoCollection[Rental] = database.getCollection[Rental]("rentals")
}
