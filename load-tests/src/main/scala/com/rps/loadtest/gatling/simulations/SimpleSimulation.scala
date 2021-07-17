package com.rps.loadtest.gatling.simulations

import io.gatling.core.Predef.{configuration, constantUsersPerSec, global, openInjectionProfileFactory}

import scala.concurrent.duration.DurationInt

class SimpleSimulation extends BasicSimulation {
  setUp(
    playRockPaperScissors
      .inject(
        constantUsersPerSec(10).during(20.minutes)
      )
      .protocols(applicationRootHttp)
  )
    .assertions(
      global.responseTime.max.lt(100),
      global.responseTime.mean.lt(50),
      global.successfulRequests.percent.gt(95)
    )
    .maxDuration(20.minutes)
}
