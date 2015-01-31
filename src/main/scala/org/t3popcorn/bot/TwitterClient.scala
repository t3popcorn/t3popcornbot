package org.t3popcorn.bot

import twitter4j._
import twitter4j.conf.ConfigurationBuilder

object SearchStreamer {
  def main(args: Array[String]) {

    val searchTerms = Array("t3popcorn", "typo3", "#TYPO3", "typoscript", "t3lib_div")

    val cb = new ConfigurationBuilder()
    cb.setDebugEnabled(true)
      .setOAuthConsumerKey(sys.env("consumerKey"))
      .setOAuthConsumerSecret(sys.env("consumerSecret"))
      .setOAuthAccessToken(sys.env("accessToken"))
      .setOAuthAccessTokenSecret(sys.env("accessTokenSecret"))
    val twitterStream = new TwitterStreamFactory(cb.build()).getInstance()

    twitterStream.addListener(Util.simpleStatusListener)
    twitterStream.filter(new FilterQuery().track(searchTerms))
    println(twitterStream)
  }
}
