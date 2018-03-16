package com.partridgetech.command.spawn.choices

import com.partridgetech.common.{FlightyIntention, FlightyViewState}
import com.partridgetech.navigation.SceneCell

class MasterScoutSpawn(flightyViewState: FlightyViewState) extends BaseSpawn(flightyViewState) {

  override def getTargetCooldown: (SceneCell, Int) = (this.getTargetSpawnCell, 0)

  def getIntention: FlightyIntention = FlightyIntention.Scout

  def getSpawnEnergy:Int = Math.max(Math.min(BaseSpawn.MaxSpawnEnergy, this.flightyViewState.requestRecord.energy / 10),
    BaseSpawn.MinimumEnergy)

  override def enact: Boolean = super.enact

  override def getTargetSpawnCell: SceneCell = this.flightyViewState.adjacentEmptyCells(this.requestRecord.timeElapsed % this.flightyViewState.adjacentEmptyCells.size)

}
