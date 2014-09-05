package models

import play.api.libs.json.Json

case class Tag(val name: String)

object Tag {
  implicit val tagFormat = Json.format[Tag]
}