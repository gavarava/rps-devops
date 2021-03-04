package com.rps.loadtest.gatling.simulations

import com.typesafe.scalalogging.StrictLogging
import io.gatling.core.Predef._
import io.gatling.core.feeder.Feeder
import io.gatling.core.structure.{ChainBuilder, ScenarioBuilder}
import io.gatling.http.Predef.http
import io.gatling.http.protocol.HttpProtocolBuilder.toHttpProtocol

import java.util.concurrent.ConcurrentLinkedQueue
import scala.util.Random

class BasicSimulation extends Simulation with StrictLogging {

  val applicationRootHttp = toHttpProtocol(http
    .baseUrl("http://localhost:8080"))

  val playerNameFeeder = Iterator.continually(Map("playerName" -> Random.alphanumeric.take(20).mkString))


  val registerPlayers = scenario("Register Player")
    .exec(registerPlayer("Player1", playerNameFeeder))
    .exec(registerPlayer("Player2", playerNameFeeder))

  val registerPlayersAndStartSession = scenario("Register & Start Session")
    .exec(registerPlayer("Player1", playerNameFeeder))
    .exec(registerPlayer("Player2", playerNameFeeder))
    .exec(startSession("Player1"))

  def registerPlayer(playerIdentity: String, feeder: Feeder[Any]): ChainBuilder = {
    feed(feeder)
      .exec(savePlayerName(playerIdentity))
      .exec(http("RegisterPlayer")
        .put(session => "/rockpaperscissors/player/" + session(playerIdentity).as[String].toLowerCase))
  }

  def startSession(playerIdentity: String): ChainBuilder = {
    exec(savePlayerName(playerIdentity))
      .exec(http("RegisterPlayer")
        .post("/rockpaperscissors/start")
        .header("Content-Type", "application/json")
        .body(StringBody("""{"player": "${""" + playerIdentity + """}"}""")))
  }

  def savePlayerName(playerIdentity: String): ChainBuilder = {
    exec(session => {
      session.set(playerIdentity, session("playerName").as[String].toLowerCase)
    })
  }
}
