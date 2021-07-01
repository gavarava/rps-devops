package com.rps.loadtest.gatling.simulations

import io.gatling.core.Predef.{atOnceUsers, configuration, global, openInjectionProfileFactory}

import scala.concurrent.duration.DurationInt

class SimpleSimulation extends BasicSimulation {
  /*  setUp(
      registerPlayer
        .inject(
          atOnceUsers(1000),
        ).protocols(applicationRootHttp)
    ).maxDuration(2 minutes)

    //TODO throttle scenarios
      setUp(
      playRockPaperScissors
        .inject(rampUsers(500).during(10.minutes))
        .throttle(reachRps(100).in(10.seconds), holdFor(10.minutes))
        .protocols(applicationRootHttp)
    ).maxDuration(2 minutes)
    */

  setUp(
    playRockPaperScissors
      .inject(
        atOnceUsers(30),
      )
      .protocols(applicationRootHttp)
  )
    .assertions(
      global.responseTime.max.lt(50),
      global.successfulRequests.percent.gt(95)
    )
    .maxDuration(2 minutes)
}
