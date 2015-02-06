package org.t3popcorn.bot

import com.typesafe.config.{ConfigFactory, Config}
import net.ceedubs.ficus.Ficus._

object Settings {

  val config: Config = ConfigFactory.load()

  def searchTerms = config.as[Array[String]]("searchTerms")

  def blackListedUsers = config.as[List[String]]("userBlackList")

}
