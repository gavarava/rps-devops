package nu.scala

import java.net.URL
import scala.io.Source
import scala.util.Using
import java.io._

object CsvToJsonConverter {

  val ROW_SEPARATOR = '\n'
  val COL_SEPARATOR = ';'

  def readFile(fileUrl: String): String = {
    var fileAsString = ""
    try {
      fileAsString = Using(Source.fromInputStream(new URL(fileUrl).openStream())) {
        // source.getLines().mkString Doing getLines removes the newlines.
        source => source.mkString // this consumes whole file as is
      }.get
      fileAsString
    } catch {
      case e: Exception => println("WARNING: Exception in readFile " + e)
        fileAsString
    }
  }

  def extractNameFromRow(row: String): String = {
    row.split(COL_SEPARATOR)(0)
  }

  /**
   * write a `String` to the `filename`.
   */
  def writeFile(filename: String, s: String): Unit = {
    val file = new File(filename)
    val bw = new BufferedWriter(new FileWriter(file))
    bw.write(s)
    bw.close()
  }

  def main(args: Array[String]): Unit = {
    val FILE_URI = "https://raw.githubusercontent.com/" +
      "MatthiasWinkelmann/firstname-database/master/firstnames.csv"
    val fileAsString = readFile(FILE_URI)

    val csvFileAsArray = fileAsString.split(ROW_SEPARATOR)
    println("Found Rows in File = " + csvFileAsArray.size)
    val namesList = csvFileAsArray.map(extractNameFromRow)

    val start = "{\"names\":[\""
    val end = "\"]}"
    val namesJson = namesList.patch(0, Nil, 1).mkString(start, "\",\"", end)
    writeFile("names.json", namesJson)
  }
}
