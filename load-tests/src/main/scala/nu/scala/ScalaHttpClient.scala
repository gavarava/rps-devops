package nu.scala

import sttp.client3._

/**
 * STTP The Scala Http Client
 * https://sttp.softwaremill.com/en/latest/examples.html#post-a-form-using-the-synchronous-backend
 */
object ScalaHttpClient {

  def main(args: Array[String]): Unit = {

    var name = "Gustaf";
    var startDate = "2021-11-01";

    var requestJson =
      s"""
    {
            "name": $name,
            "startDate": $startDate
    }"""

    println(requestJson)

    val request = basicRequest
      .body(requestJson)
      .post(uri"https://google.co.in")

    val backend = HttpURLConnectionBackend()
    val response = request.send(backend)

    println(response.body)
    println(response.headers)

    backend.close()
  }

}
