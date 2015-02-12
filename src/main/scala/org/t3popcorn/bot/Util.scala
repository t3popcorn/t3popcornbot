package org.t3popcorn.bot

import cmu.arktweetnlp.{Tagger, Twokenize}
import twitter4j._

import scala.util.Random
import org.slf4j.LoggerFactory

object Util extends TwitterInstance {

  def simpleStatusListener = new StatusListener() {

    def onStatus(status: Status) {

      val two = new Tagger
      two.loadModel("src/main/resources/model.20120919")

      val replyOn =
        !status.isRetweet &&
        !Settings.blackListedUsers.contains(status.getUser.getScreenName) &&
        !Settings.environment.equals("dev")

      if (replyOn && Random.nextInt(8) == 4) {
        val reply = Random.shuffle(Settings.replies).head

        logger.info(status.getText)
        val statusAuthor = status.getUser.getScreenName

        val text = "@" + statusAuthor + " " + reply
        val update = new StatusUpdate(text).inReplyToStatusId(status.getId)
        twitter.updateStatus(update)

      }
      if (status.getText.contains("#t3popcorn") && replyOn) {
        twitter.createFavorite(status.getId)
        twitter.retweetStatus(status.getId)
        twitter.createFriendship(status.getUser.getId)
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

  def logger = LoggerFactory.getLogger("t3popcorn")

}
