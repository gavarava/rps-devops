package com.rps.loadtest.gatling.simulations

import io.gatling.core.Predef.{atOnceUsers, openInjectionProfileFactory}

import scala.concurrent.duration.DurationInt

class SimpleSimulation extends BasicSimulation {
  setUp(
    registerPlayersAndStartSession
      .inject(
      atOnceUsers(1500),
    ).protocols(applicationRootHttp)
  ).maxDuration(2 minutes)

}
