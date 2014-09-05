package models

import play.api.libs.json.Json
import reactivemongo.bson.BSONObjectID
import play.modules.reactivemongo.json.BSONFormats._
import org.joda.time.DateTime

case class Company(
  val _id: Option[BSONObjectID],
  val name: String,
  val description: String,
  val active: Boolean = false,
  val contactInformation: ContactInformation,
  val tags: Option[List[Tag]],
  creationDate: Option[DateTime] = Some(new DateTime()),
  updateDate: Option[DateTime] = Some(new DateTime()))

object Company {
  implicit val companyFormat = Json.format[Company]
}