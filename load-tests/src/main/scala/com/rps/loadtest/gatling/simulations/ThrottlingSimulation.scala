package com.rps.loadtest.gatling.simulations

import io.gatling.core.Predef.{configuration, constantUsersPerSec, global, holdFor, jumpToRps, openInjectionProfileFactory, reachRps}

import scala.concurrent.duration.DurationInt

class ThrottlingSimulation extends BasicSimulation {
  // generate an open workload injection profile
  // with levels of 10, 15, 20, 25 and 30 arriving users per second
  // each level lasting 10 seconds
  // separated by linear ramps lasting 10 seconds
  setUp(
    playRockPaperScissors.inject(
      constantUsersPerSec(100).during(30.minutes))
      .throttle(
        reachRps(50).in(10.seconds),
        holdFor(5.minutes),
        jumpToRps(100),
        holdFor(1.minute)
      )
    )
    .maxDuration(10.minutes)
    .protocols(applicationRootHttp)

    /*
  setUp(scn.inject(constantUsersPerSec(100).during(30.minutes))).throttle(
  reachRps(100).in(10.seconds),
  holdFor(1.minute),
  jumpToRps(50),
  holdFor(2.hours)
)
     */
  .assertions(
    global.responseTime.max.lt(50),
    global.successfulRequests.percent.gt(95)
  ).maxDuration(10 minutes)
}
