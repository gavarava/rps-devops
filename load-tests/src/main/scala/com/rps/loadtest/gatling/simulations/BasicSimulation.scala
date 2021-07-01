package com.rps.loadtest.gatling.simulations

import com.rps.loadtest.gatling.simulations.FileUtils.readFile
import com.typesafe.scalalogging.StrictLogging
import io.gatling.core.Predef._
import io.gatling.core.feeder.Feeder
import io.gatling.core.session.Session
import io.gatling.core.structure.{ChainBuilder, ScenarioBuilder}
import io.gatling.http.Predef.{http, _}
import io.gatling.http.protocol.HttpProtocol
import io.gatling.http.protocol.HttpProtocolBuilder.toHttpProtocol

import java.util.UUID

class BasicSimulation extends Simulation with StrictLogging {

  val applicationRootHttp: HttpProtocol = toHttpProtocol(http
    .baseUrl("http://localhost:8080"))

  val playerNameFeeder: Iterator[Map[String, String]] = Iterator.continually(Map("playerName" -> UUID.randomUUID().toString))


  val registerPlayer: ScenarioBuilder = scenario("Register Player")
    .exec(
      registerPlayer("Player1", playerNameFeeder)
    )

  val playRockPaperScissors: ScenarioBuilder = scenario("Play Rock Paper Scissors in concurrent sessions")
    .exec(registerPlayer("Player1", playerNameFeeder))
    .exec(registerPlayer("Player2", playerNameFeeder))
    .exec(startSession("Player1"))
    .exec(joinSession("Player2"))
    .exec(session => printSessionVariable(session, "gameId"))
    .exec(play("Player1", "ROCK"))
    .exec(play("Player2", "SCISSORS"))
    .exec(checkResult("Player1"))
    .exec(checkResult("Player2"))
    .exec(session => printSessionVariable(session, "winner"))
    .exitHere

  def registerPlayer(playerIdentityKey: String, feeder: Feeder[Any]): ChainBuilder = {
    feed(feeder)
      .exec(savePlayerName(playerIdentityKey))
      .exec(http("RegisterPlayer")
        .put(session => "/rockpaperscissors/player/" + session(playerIdentityKey).as[String].toLowerCase))
  }

  def startSession(playerIdentityKey: String): ChainBuilder = {
    exec(
      http("StartSession")
        .post("/rockpaperscissors/start")
        .header("Content-Type", "application/json")
        .body(StringBody(session => createStartSessionRequest(session, playerIdentityKey)))
        .check(status.find.in(200, 201))
        .check(jsonPath("$.gameId").saveAs("gameId"))
    )
  }

  def joinSession(playerIdentityKey: String): ChainBuilder = {
    exec(
      http("JoinSession")
        .post("/rockpaperscissors/join")
        .header("Content-Type", "application/json")
        .body(StringBody(session =>
          createSessionRequest(session("gameId").as[String], session(playerIdentityKey).as[String])))
        .notSilent
    )
  }

  def play(playerIdentityKey: String, moveMadeByPlayer: String): ChainBuilder = {
    exec(
      http("Play")
        .post("/rockpaperscissors/play")
        .header("Content-Type", "application/json")
        .body(StringBody(session =>
          createPlayRequest(session("gameId").as[String], session(playerIdentityKey).as[String], moveMadeByPlayer)))
        .notSilent
    )
  }

  def checkResult(playerIdentityKey: String): ChainBuilder = {
    exec(
      http("CheckResult")
        .post("/rockpaperscissors/result")
        .header("Content-Type", "application/json")
        .body(StringBody(session =>
          createSessionRequest(session("gameId").as[String], session(playerIdentityKey).as[String])))
        .check(jsonPath("$.winner").saveAs("winner"))
        .notSilent
    )
  }

  def createStartSessionRequest(session: Session, playerIdentityKey: String): String = {
    readFile("/rps-requests/start-session-request.json")
      .replace("player-name-value", session(playerIdentityKey).as[String])
  }

  def createSessionRequest(gameId: String, playerName: String): String = {
    readFile("/rps-requests/join-session-request.json")
      .replace("gameId-value", gameId)
      .replace("player-name-value", playerName)
  }

  def createPlayRequest(gameId: String, playerName: String, move: String): String = {
    readFile("/rps-requests/play-request.json")
      .replace("gameId-value", gameId)
      .replace("player-name-value", playerName)
      .replace("move-value", move)
  }

  def savePlayerName(playerIdentityKey: String): ChainBuilder = {
    exec(session => {
      session.set(playerIdentityKey, session("playerName").as[String].toLowerCase)
    })
  }

  def saveGameId(gameId: String): ChainBuilder = {
    exec(session => {
      session.set(gameId, session("gameId").as[String].toLowerCase)
    })
  }

  private def printSessionVariable(session: Session, key: String) = {
    println(s"key=$key, value=" + session(key).as[String])
    session
  }
}
