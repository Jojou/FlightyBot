package com.partridgetech.command.move.choices

import com.partridgetech.attribute.OutputCommand
import com.partridgetech.common.FlightyViewState
import com.partridgetech.navigation.SceneCell

class BlastRadiusAttackMove(flightyViewState: FlightyViewState) extends BaseMove(flightyViewState) {

  override def enact: Boolean = {
    this.flightyViewState.closestEnemySlave.isDefined && this.flightyViewState.closestFriendlySlave.isDefined &&
      this.flightyViewState.getMainCellDistanceTo(this.flightyViewState.closestEnemySlave.get) < BaseMove.BlastRadiusActionDistance &&
      this.sceneView.getClosestCellToTarget(this.flightyViewState.closestEnemySlave.get, Seq(this.flightyViewState.mainCell
      )++this.flightyViewState.friendlySlaves) == this.flightyViewState.mainCell
  }

  override def getUpdatedTargetAndCooldown:(SceneCell, Int) = this.flightyViewState.closestEnemySlave.get -> 1
}
