package org.t3popcorn.bot

import twitter4j._
import twitter4j.conf.ConfigurationBuilder

/**
 * Gets a Twitter instance
 */
trait TwitterInstance extends AuthConfiguration {
  val twitter: Twitter = new TwitterFactory(cb.build()).getInstance()
}

/**
 * Central entrypoint for the authentication
 */
trait AuthConfiguration {
  val cb = new ConfigurationBuilder()
  cb.setDebugEnabled(true)
    .setOAuthConsumerKey(sys.env("consumerKey"))
    .setOAuthConsumerSecret(sys.env("consumerSecret"))
    .setOAuthAccessToken(sys.env("accessToken"))
    .setOAuthAccessTokenSecret(sys.env("accessTokenSecret"))
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
