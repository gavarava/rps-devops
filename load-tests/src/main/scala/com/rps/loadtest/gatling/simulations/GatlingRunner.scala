package com.rps.loadtest.gatling.simulations

import io.gatling.app.Gatling
import io.gatling.core.config.GatlingPropertiesBuilder

/**
 * Use this only for debugging
 */
object GatlingRunner {

  def main(args: Array[String]): Unit = {
    // this is where you specify the class you want to run
    val simClass = classOf[OpenWorkloadSimulation].getName

    val props = new GatlingPropertiesBuilder
    props.simulationClass(simClass)
    Gatling.fromMap(props.build)
  }
}
