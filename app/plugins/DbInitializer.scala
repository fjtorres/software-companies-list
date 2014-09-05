package plugins

import scala.concurrent.Await
import scala.concurrent.duration.DurationInt

import controllers.CompanyController
import controllers.TestData
import play.api.Application
import play.api.Logger
import play.api.Plugin
import play.api.libs.concurrent.Execution.Implicits.defaultContext

class DbInitializer(application: Application) extends Plugin {

  override def onStart() {
    import play.api.libs.concurrent.Execution.Implicits._
    Logger.debug("initializeData")
    
    
    def collection = CompanyController.collection
    def initialize = for {
      _ <- collection.create()
      _ <- collection.stats() map {
        case stats if stats.count == 0 =>
          Logger.debug("initializing")
          TestData.testCompanies map {
            data => collection.insert(data)
          }
        case _ =>
          Logger.debug("already initialized")
      }
    } yield "done"
    try {
      Await.result( initialize, 1 minute)
    } catch {
      case e : Exception => Logger.error(s"Error initializing database: ${e.getMessage}")
    }
  }

}