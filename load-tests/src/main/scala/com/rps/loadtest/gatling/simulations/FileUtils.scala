package com.rps.loadtest.gatling.simulations

import scala.io.Source
import scala.util.Using

object FileUtils {

  def readFile(filename: String): String = {
    var fileAsString = ""
    try {
      fileAsString = Using(Source.fromInputStream(getClass.getResourceAsStream(filename))) {
        source => source.getLines().mkString
      }.get
      fileAsString
    } catch {
      case e: Exception => println("WARNING: Exception in readFile " + e)
        fileAsString
    }
  }
}
