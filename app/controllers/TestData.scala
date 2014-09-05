package controllers

import models._
import reactivemongo.bson.BSONObjectID

object TestData {

  /*
   * Company(
      name = "47 degress",
      description = "",
      active = true,
      contactInformation = ContactInformation(
        address = "",
        phoneNumbers = List(),
        emails = List(),
        webSite = ""),
      tags = List())
   * 
   */

  val testCompanies: List[Company] = List(

    Company(
      _id = None,
      name = "47 degress",
      description = "Sómos un estudio de desarrollo de aplicaciónes móviles enfocado al diseño de producto y a crear experiencias digitales.",
      active = true,
      contactInformation = ContactInformation(
        address = "Calle Real 150, 11100 San Fernando, Cádiz, España",
        phoneNumbers = Option("690-051-056"),
        emails = Option("hola@47deg.com"),
        webSite = "http://www.47deg.com/"),
      tags = Option(List())),

    Company(
      _id = None,
      name = "Crononauta",
      description = "Somos un equipo de desarrolladores web y administradores de sistemas con amplia experiencia. Ofrecemos servicios de definición y gestión de proyectos, desarrollo a medida y administración de sistemas.",
      active = true,
      contactInformation = ContactInformation(
        address = "Calle Guatemala (Edificio Andalucía), Jerez de la frontera, Cádiz, España",
        phoneNumbers = Option("956 300 383"),
        emails = Option("hola@crononauta.com"),
        webSite = "http://crononauta.com/"),
      tags = Option(List())))
}