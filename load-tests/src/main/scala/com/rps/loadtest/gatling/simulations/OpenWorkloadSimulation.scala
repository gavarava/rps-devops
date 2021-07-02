package com.rps.loadtest.gatling.simulations

import io.gatling.core.Predef.{configuration, global, incrementUsersPerSec, openInjectionProfileFactory}

import scala.concurrent.duration.DurationInt

class OpenWorkloadSimulation extends BasicSimulation {
  // generate an open workload injection profile
  // with levels of 10, 15, 20, 25 and 30 arriving users per second
  // each level lasting 10 seconds
  // separated by linear ramps lasting 10 seconds
  setUp(
    playRockPaperScissors.inject(
      incrementUsersPerSec(5) // Double
        .times(5)
        .eachLevelLasting(10.seconds)
        .separatedByRampsLasting(10.seconds)
        .startingFrom(10) // Double
    ).protocols(applicationRootHttp)
  ).assertions(
    global.responseTime.max.lt(50),
    global.successfulRequests.percent.gt(95)
  ).maxDuration(10 minutes)
}
