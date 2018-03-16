package com.partridgetech.command.explode.choices

import com.partridgetech.attribute.OutputCommand
import com.partridgetech.common.FlightyViewState

class ProximityExplode(flightyViewState: FlightyViewState) extends BaseExplode(flightyViewState) {

  def enact: Boolean = {
    this.flightyViewState.enemyMaster.isDefined && this.flightyViewState.getMainCellDistanceTo(this.flightyViewState.enemyMaster.get) <= ProximityExplode.
      Detonation_Proximity
  }

  def getOutcome: OutputCommand = {
    OutputCommand.Explode(3)
  }
}

object ProximityExplode {
  val Detonation_Proximity = 2
}