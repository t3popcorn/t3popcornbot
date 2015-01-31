package org.t3popcorn.bot

import twitter4j._
import twitter4j.conf.ConfigurationBuilder

object SearchStreamer extends AuthConfiguration{
  def main(args: Array[String]) {

    val twitterStream = new TwitterStreamFactory(cb.build()).getInstance()

    twitterStream.addListener(Util.simpleStatusListener)
    twitterStream.filter(new FilterQuery().track(Util.searchTerms))
    println(twitterStream)
  }
}
