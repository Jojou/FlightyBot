package com.partridgetech.command.move.choices

import com.partridgetech.common.FlightyViewState
import com.partridgetech.navigation.SceneCell

class BlastRadiusEvadeMove(flightyViewState: FlightyViewState) extends BaseMove(flightyViewState) {

  override def enact: Boolean = {
    this.flightyViewState.closestEnemySlave.isDefined && this.flightyViewState.closestFriendlySlave.isDefined &&
      this.flightyViewState.getMainCellDistanceTo(this.flightyViewState.closestEnemySlave.get) < BaseMove.BlastRadiusActionDistance &&
      this.sceneView.getClosestCellToTarget(this.flightyViewState.closestEnemySlave.get, Seq(this.flightyViewState.mainCell
      ) ++ this.flightyViewState.friendlySlaves) != this.flightyViewState.mainCell
  }

  def getTargetCell: SceneCell = {
    this.sceneView.getCell(this.flightyViewState.mainCell, this.flightyViewState.getMainCellMoveFrom(this.flightyViewState.closestEnemySlave.get))
  }

  override def getUpdatedTargetAndCooldown:(SceneCell, Int) =  {
    val cellsOutsideRange = this.flightyViewState.sceneView.getAdjacentCells(this.flightyViewState.mainCell)
      .filter(cell => cell.getMoves(this.flightyViewState.closestEnemySlave.get) < BaseMove.BlastRadiusActionDistance)

    val targetCell = this.sceneView.getClosestCellToTarget(this.requestRecord.targetCell, cellsOutsideRange)

    targetCell -> 1
  }
}
