package controllers

import models.Company
import play.api.data.Form
import play.api.data.Forms._
import play.api.data.format.Formats.stringFormat
import play.api.data.validation.Constraints.pattern
import play.api.mvc.Action
import play.api.mvc.Controller
import play.modules.reactivemongo.MongoController
import play.modules.reactivemongo.json.collection.JSONCollection
import reactivemongo.bson.BSONObjectID
import play.api.libs.json.Json
import play.api.libs.json.JsObject
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.libs.json.JsValue

import play.modules.reactivemongo.json.BSONFormats._

object CompanyController extends Controller with MongoController {

  // Form definition with validations
  val suggestForm = Form(mapping(
    "id" -> optional(of[String] verifying pattern(
      "[a-fA-F0-9]{24}".r,
      "constraint.objectId",
      "error.objectId")),
    "name" -> nonEmptyText,
    "description" -> nonEmptyText,
    "emails" -> optional(text),
    "phoneNumbers" -> optional(text),
    "address" -> nonEmptyText,
    "webSite" -> nonEmptyText,
    "tags" -> optional(text)) {
      (id, name, description, emails, phoneNumbers, address, webSite, tags) =>
        new CompanyData(
          id.map(new BSONObjectID(_)),
          name,
          description,
          emails,
          phoneNumbers,
          address,
          webSite,
          tags)
    } {
      company =>
        Some(
          (
            company.id.map(_.stringify),
            company.name,
            company.description,
            company.emails,
            company.phoneNumbers,
            company.address,
            company.webSite,
            company.tags))
    })

  val searchForm = Form(single("query" -> optional(text)))

  def collection: JSONCollection = db.collection[JSONCollection]("companies")

  // Show list
  def list = Action.async { implicit request =>

    val futureList = collection
      .find(Json.obj())
      .sort(Json.obj("creationDate" -> -1))
      .cursor[Company]
      .collect[List](upTo = 50)

    for {
      companies <- futureList
    } yield Ok(views.html.list(companies, suggestForm, searchForm))

  }

  // Show filtered list
  def search = Action.async { implicit request =>

    val queryForm = searchForm.bindFromRequest
    val queryFormValue = queryForm.get

    val query = queryFormValue match {
      case None => Json.obj()
      case Some(filter) => Json.obj("name" -> Json.obj("$regex" -> (filter + ".*"), "$options" -> "i"))
    }

    val futureList = collection
      .find(query)
      .sort(Json.obj("creationDate" -> -1))
      .cursor[Company]
      .collect[List](upTo = 50)

    for {
      companies <- futureList
    } yield Ok(views.html.list(companies, suggestForm, queryForm))

  }

  def detail(id: String) = Action.async { implicit request =>
    val oid = new BSONObjectID(id)

    val query = Json.obj(("_id" -> Json.obj("$oid" -> oid.stringify)));

    val future = collection
      .find(query)
      .one[Company]

    future.map {
      case Some(item) => Ok(views.html.detail(item))
      case None => NotFound(Json.obj("message" -> "No such item"))
    }
  }

  // Submit suggestion form with data.
  def suggest = Action.async { implicit request =>
    suggestForm.bindFromRequest.fold(

      // Return request with errors
      formWithErrors => {
        list().apply(request)
      },

      //Success submit without errors
      companyData => {
        val suggestCompany = CompanyData.toCompany(companyData)
        collection.insert(suggestCompany)
        list().apply(request)
      })
  }

}