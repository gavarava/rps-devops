package com.rps.loadtest.gatling.simulations

import com.typesafe.scalalogging.StrictLogging
import io.gatling.core.Predef._
import scala.util.Random
import io.gatling.http.Predef.http
import io.gatling.http.protocol.HttpProtocolBuilder.toHttpProtocol

class BasicSimulation extends Simulation with StrictLogging {

  val applicationRootHttp = toHttpProtocol(http
    .baseUrl("http://localhost"))

  val playerNameFeeder = Iterator.continually(Map("player" -> Random.alphanumeric.take(20).mkString))

  val createPlayerScenario = scenario("Create Player")
    .exec(http("request_1")
      .put("/rockpaperscissors/player/" + feed(playerNameFeeder)))

}
