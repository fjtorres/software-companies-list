package controllers

import reactivemongo.bson.BSONObjectID
import models.Tag
import models.Company
import models.ContactInformation

case class CompanyData(
  val id: Option[BSONObjectID],
  val name: String,
  val description: String,
  val emails: Option[String],
  val phoneNumbers: Option[String],
  val address: String,
  val webSite: String,
  val tags: Option[String])

object CompanyData {
  // Data separator for multiple form fields (emails, phone numbers and tags).
  val dataSeparator = ","

  // Conver from CompanyData to Company Model
  def toCompany(data: CompanyData) = {

    // Parse tags
    def getTags = {
      data.tags match {
        case None => Nil
        case Some(value) => value.split(dataSeparator).map(str => Tag(str)).toList
      }
    }

    // Parse emails and phone numbers
    def splitString(str: Option[String]) = {
      str match {
        case None => Nil
        case Some(value) => value.split(dataSeparator).toList
      }
    }

    val company = Company(
      _id = data.id,
      name = data.name,
      description = data.description,
      contactInformation = ContactInformation(
        address = data.address,
        phoneNumbers = data.phoneNumbers,
        emails = data.emails,
        webSite = data.webSite),
      tags = Some(getTags))

    company
  }
}