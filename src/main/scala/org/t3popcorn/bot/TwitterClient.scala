package org.t3popcorn.bot

import twitter4j._

object SearchStreamer {
  def main(args: Array[String]) {

    val searchTerms = Array("t3popcorn", "typo3")

    val twitterStream = new TwitterStreamFactory().getInstance
    twitterStream.addListener(Util.simpleStatusListener)
    twitterStream.filter(new FilterQuery().track(searchTerms))
    println(twitterStream)
  }
}
