package com.partridgetech.common

import com.partridgetech.model.ReactRequestRecord
import com.partridgetech.navigation.SceneView

abstract class FlightyChoice[V](val flightyViewState: FlightyViewState) {

  val requestRecord: ReactRequestRecord = flightyViewState.requestRecord

  val sceneView: SceneView = flightyViewState.sceneView

  def enact: Boolean

  def getOutcome: V
}
