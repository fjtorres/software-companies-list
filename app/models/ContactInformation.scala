package models

import play.api.libs.json.Json

case class ContactInformation(
  val address: String,
  val phoneNumbers: Option[String],
  val emails: Option[String],
  val webSite: String)

object ContactInformation {
  implicit val contactInformationFormat = Json.format[ContactInformation]
}