package org.t3popcorn.bot

import com.typesafe.config.{ConfigFactory, Config}
import net.ceedubs.ficus.Ficus._

object Settings {

  val config: Config = ConfigFactory.load()

  def searchTerms: Array[String] = config.as[Array[String]]("searchTerms")

  def blackListedUsers: List[String] = config.as[List[String]]("userBlackList")

  def replies: List[String] = config.as[List[String]]("replies")

  def environment: String = sys.env("T3BOTENV")

}
