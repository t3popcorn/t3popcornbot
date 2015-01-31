package org.t3popcorn.bot

import twitter4j._
import twitter4j.conf.ConfigurationBuilder
import collection.JavaConversions._

/**
 * Gets a Twitter instance set up and ready to use.
 */
trait TwitterInstance extends AuthConfiguration {
  val twitter = new TwitterFactory(cb.build()).getInstance()
}

trait AuthConfiguration {
  val cb = new ConfigurationBuilder()
  cb.setDebugEnabled(true)
    .setOAuthConsumerKey(sys.env("consumerKey"))
    .setOAuthConsumerSecret(sys.env("consumerSecret"))
    .setOAuthAccessToken(sys.env("accessToken"))
    .setOAuthAccessTokenSecret(sys.env("accessTokenSecret"))
}

/**
 * Go through the N most recent tweets that have mentioned
 * the authenticating user, and reply "OK." to them (ensuring
 * to include the author of the original tweet and any other
 * entities mentioned in it).
 */
object ReplyOK extends TwitterInstance {

  def reply(args: Array[String]) {
    val num = if (args.length == 1) args(0).toInt else 10
    val userName = twitter.getScreenName
    val statuses = twitter.getMentionsTimeline.take(num)
    statuses.foreach { status => {
      val statusAuthor = status.getUser.getScreenName
      val mentionedEntities = status.getUserMentionEntities.map(_.getScreenName).toList
      val participants = (statusAuthor :: mentionedEntities).toSet - userName
      val text = participants.map(p => "@" + p).mkString(" ") + " OK."
      val reply = new StatusUpdate(text).inReplyToStatusId(status.getId)
      Util.logger.info("Replying " + text)
      twitter.updateStatus(reply)
    }
                     }

  }

}

/**
 * A trait with checkAndWait function that checks whether the
 * rate limit has been hit and wait if it has.
 *
 * This ignores the fact that different request types have different
 * limits, but it keeps things simple.
 */
trait RateChecker {

  /**
   * See whether the rate limit has been hit, and wait until it
   * resets if so. Waits 10 seconds longer than the reset time to
   * ensure the time is sufficient.
   *
   * This is surely not an optimal solution, but it seems to do
   * the trick.
   */
  def checkAndWait(response: TwitterResponse, verbose: Boolean = false) {
    val rateLimitStatus = response.getRateLimitStatus
    if (verbose) Util.logger.info("RLS: " + rateLimitStatus)

    if (rateLimitStatus != null && rateLimitStatus.getRemaining == 0) {
      Util.logger.error("*** You hit your rate limit. ***")
      val waitTime = rateLimitStatus.getSecondsUntilReset + 10
      Util.logger.info("Waiting " + waitTime + " seconds ( "
              + waitTime / 60.0 + " minutes) for rate limit reset.")
      Thread.sleep(waitTime * 1000)
    }
  }

}
