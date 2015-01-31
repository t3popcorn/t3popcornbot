package org.t3popcorn.bot

import java.io.InputStream

import twitter4j._

import scala.util.Random
import scala.io.Source

object Util extends TwitterInstance {
  def simpleStatusListener = new StatusListener() {
    def onStatus(status: Status) {
      if (!status.isRetweet) {
        val reply = Random.shuffle(Util.replies).head

        println(status.getText)
        val statusAuthor = status.getUser.getScreenName
        twitter.createFavorite(status.getId)
        val text = "@" + statusAuthor + " " + reply
        val update = new StatusUpdate(text).inReplyToStatusId(status.getId)
        twitter.updateStatus(update)
      }
    }

    def onDeletionNotice(statusDeletionNotice: StatusDeletionNotice) {}

    def onTrackLimitationNotice(numberOfLimitedStatuses: Int) {}

    def onException(ex: Exception) {
      ex.printStackTrace
    }

    def onScrubGeo(arg0: Long, arg1: Long) {}

    def onStallWarning(warning: StallWarning) {}

  }

  def replies = Source.fromFile("src/main/resources/replies").getLines().toList

}