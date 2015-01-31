import NativePackagerKeys._

packageArchetype.java_application

name := "t3popcornbot"

version := "1.0"

scalaVersion := "2.11.5"

libraryDependencies += "org.twitter4j" % "twitter4j-core" % "4.0.2"

libraryDependencies += "org.twitter4j" % "twitter4j-stream" % "4.0.2"

libraryDependencies ++= Seq("org.slf4j" % "slf4j-api" % "1.7.10",
                            "org.slf4j" % "slf4j-simple" % "1.7.10")