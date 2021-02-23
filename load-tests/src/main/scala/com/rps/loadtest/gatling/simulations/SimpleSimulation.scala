package com.rps.loadtest.gatling.simulations

import io.gatling.core.Predef.{atOnceUsers, nothingFor, openInjectionProfileFactory}

import scala.concurrent.duration.DurationInt

class SimpleSimulation extends BasicSimulation {
  // Use this setUp to test the scenarios with single users
  setUp(
    createPlayerScenario.inject(
      atOnceUsers(10000),
    ).protocols(applicationRootHttp)
  ).maxDuration(1 minutes)
}
