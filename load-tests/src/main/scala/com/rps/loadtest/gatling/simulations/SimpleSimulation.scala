package com.rps.loadtest.gatling.simulations

import io.gatling.core.Predef.{atOnceUsers, feed, nothingFor, openInjectionProfileFactory}

import scala.concurrent.duration.DurationInt

class SimpleSimulation extends BasicSimulation {
  setUp(
    registerPlayers
      .inject(
      atOnceUsers(10),
    ).protocols(applicationRootHttp)
  ).maxDuration(1 minutes)

}
