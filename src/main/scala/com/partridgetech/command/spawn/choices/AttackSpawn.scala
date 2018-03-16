package com.partridgetech.command.spawn.choices

import com.partridgetech.common.{FlightyIntention, FlightyViewState}
import com.partridgetech.navigation.SceneCell

class AttackSpawn(flightyViewState: FlightyViewState) extends BaseSpawn(flightyViewState) {

  override def getTargetCooldown: (SceneCell, Int) = {
    (this.flightyViewState.enemyMaster.get, this.getTargetSpawnCell.getMoves(this.flightyViewState.enemyMaster.get))
  }

  def getIntention: FlightyIntention = FlightyIntention.Attack

  def getSpawnEnergy:Int = Math.max(Math.min(BaseSpawn.MaxSpawnEnergy, this.flightyViewState.requestRecord.energy / 20), BaseSpawn.MinimumEnergy)

  override def enact: Boolean = super.enact && this.flightyViewState.enemyMaster.isDefined

  def getTargetSpawnCell: SceneCell = this.flightyViewState.sceneView.getClosestCellToTarget(this.flightyViewState
    .enemyMaster.get, this.flightyViewState.adjacentEmptyCells)
}