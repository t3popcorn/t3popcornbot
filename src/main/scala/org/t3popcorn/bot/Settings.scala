package org.t3popcorn.bot

import com.typesafe.config.{ConfigFactory, Config}
import net.ceedubs.ficus.Ficus._

import scala.io.Source

object Settings {

  val config: Config = ConfigFactory.load()

  def searchTerms = config.as[Array[String]]("searchTerms")

  def blackListedUsers = config.as[List[String]]("userBlackList")

  def replies = config.as[List[String]]("replies")

  def environment = sys.env("T3BOTENV")

}
