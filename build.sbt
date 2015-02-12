import NativePackagerKeys._

packageArchetype.java_application

name := "t3popcornbot"

version := "1.0"

scalaVersion := "2.11.5"

libraryDependencies ++= Seq(
  "org.twitter4j" % "twitter4j-core" % "4.0.2",
  "org.twitter4j" % "twitter4j-stream" % "4.0.2",
  "org.slf4j" % "slf4j-api" % "1.7.10",
  "org.slf4j" % "slf4j-simple" % "1.7.10",
  "net.ceedubs" %% "ficus" % "1.1.2",
  "org.scalatest" %% "scalatest" % "2.2.4" % "test",
  "edu.cmu.cs" % "ark-tweet-nlp" % "0.3.2"
)
