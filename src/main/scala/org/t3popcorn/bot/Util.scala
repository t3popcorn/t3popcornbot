package org.t3popcorn.bot

import twitter4j._

import scala.util.Random
import scala.io.Source
import org.slf4j.LoggerFactory

object Util extends TwitterInstance {

  def simpleStatusListener = new StatusListener() {

    def onStatus(status: Status) {
      if (!status.isRetweet && !blackListedUsers.contains(status.getUser.getScreenName)) {
        val reply = Random.shuffle(Util.replies).head

        logger.info(status.getText)
        val statusAuthor = status.getUser.getScreenName

        val text = "@" + statusAuthor + " " + reply
        val update = new StatusUpdate(text).inReplyToStatusId(status.getId)
        twitter.updateStatus(update)

        if (status.getText.contains("t3popcorn")) {
          twitter.createFavorite(status.getId)
          twitter.retweetStatus(status.getId)
        }
      }

    }

    def onDeletionNotice(statusDeletionNotice: StatusDeletionNotice) {}

    def onTrackLimitationNotice(numberOfLimitedStatuses: Int) {}

    def onException(ex: Exception) {
      logger.error(ex.getMessage)
    }

    def onScrubGeo(arg0: Long, arg1: Long) {}

    def onStallWarning(warning: StallWarning) {}

  }

  def replies = Source.fromFile("src/main/resources/replies").getLines().toList

  def blackListedUsers = Source.fromFile("src/main/resources/userBlackList").getLines().toList

  def searchTerms = Source.fromFile("src/main/resources/searchTerms").getLines().toArray

  def logger = LoggerFactory.getLogger("t3popcorn")

}
