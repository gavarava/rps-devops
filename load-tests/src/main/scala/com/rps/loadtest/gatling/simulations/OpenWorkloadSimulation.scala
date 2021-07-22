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
      incrementUsersPerSec(5).times(5) // incrementUsersPerSec in 5 steps (nbOfSteps)
        .eachLevelLasting(10.seconds) // during 10 seconds, usersPerSecond will stay at the same value
        .startingFrom(10) // Starting from 10 users
        .separatedByRampsLasting(10.seconds) // 10 seconds after, it will increment to a value of 10 + incrementUsersPerSec
    ).protocols(applicationRootHttp)
  ).assertions(
    global.responseTime.max.lt(50),
    global.successfulRequests.percent.gt(95)
  ).maxDuration(10 minutes)
  // The key idea is that this ramp is on the value usersPerSecond (5).
}
