package models

import io.circe.syntax._
import io.circe._

import org.bson.types.ObjectId


case class Rental(_id: ObjectId, name: String) {
  require(name != null, "Name not informed")
  require(name.nonEmpty, "Name cannot be empty")
}

object Rental {
  implicit val encoder: Encoder[Rental] = (myRental: Rental) => {
    Json.obj(
      "id" -> myRental._id.toHexString.asJson,
      "name" -> myRental.name.asJson
    )
  }

  implicit val decoder: Decoder[Rental] = (c: HCursor) => {
    for {
      name <- c.downField("name").as[String]
    } yield Rental(ObjectId.get(), name)
  }
}
