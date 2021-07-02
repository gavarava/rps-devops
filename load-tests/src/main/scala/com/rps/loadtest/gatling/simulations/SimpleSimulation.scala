package com.rps.loadtest.gatling.simulations

import io.gatling.core.Predef.{atOnceUsers, configuration, global, openInjectionProfileFactory}

import scala.concurrent.duration.DurationInt

class SimpleSimulation extends BasicSimulation {
  setUp(
    playRockPaperScissors
      .inject(
        atOnceUsers(100),
      )
      .protocols(applicationRootHttp)
  )
    .assertions(
      global.responseTime.max.lt(50),
      global.successfulRequests.percent.gt(95)
    )
    .maxDuration(2 minutes)
}
