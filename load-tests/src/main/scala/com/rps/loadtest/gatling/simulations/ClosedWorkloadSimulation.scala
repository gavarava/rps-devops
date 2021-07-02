package com.rps.loadtest.gatling.simulations

import io.gatling.core.Predef.{closedInjectionProfileFactory, configuration, global, incrementConcurrentUsers}

import scala.concurrent.duration.DurationInt

class ClosedWorkloadSimulation extends BasicSimulation {
  // generate a closed workload injection profile
  // with levels of 10, 15, 20, 25 and 30 concurrent users
  // each level lasting 10 seconds
  // separated by linear ramps lasting 10 seconds
  setUp(
    playRockPaperScissors.inject(
      incrementConcurrentUsers(5) // Int
        .times(5)
        .eachLevelLasting(10.seconds)
        .separatedByRampsLasting(10.seconds)
        .startingFrom(10) // Int
    )
  ).protocols(applicationRootHttp).assertions(
    global.responseTime.max.lt(50),
    global.successfulRequests.percent.gt(95)
  ).maxDuration(10 minutes)
}
