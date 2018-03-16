package com.partridgetech.command.explode.choices

import com.partridgetech.attribute.OutputCommand
import com.partridgetech.common.{FlightyViewState, SceneMinion}

class EfficientExplode(flightyViewState: FlightyViewState) extends BaseExplode(flightyViewState) {

  def enact: Boolean = {
    this.bestRadiusToDamage._2 > this.requestRecord.energy * 1.1 && !SceneMinion.CONSUMABLE_MINIONS.contains(this.requestRecord.targetCell.minion)
  }

  def getOutcome: OutputCommand = {
    OutputCommand.Explode(this.bestRadiusToDamage._1)
  }
}
