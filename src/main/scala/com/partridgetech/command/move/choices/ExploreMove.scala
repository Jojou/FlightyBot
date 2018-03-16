package com.partridgetech.command.move.choices

import com.partridgetech.common.{FlightyMove, FlightyViewState, SceneMinion}
import com.partridgetech.navigation.SceneCell

import scala.util.Random

class ExploreMove (flightyViewState: FlightyViewState) extends BaseMove(flightyViewState) {

  val simplePath: List[SceneCell] = this.sceneView.getCellsBetween(this.flightyViewState.mainCell, this.requestRecord.targetCell)

  override def updateTargetCell:Boolean = super.updateTargetCell || this.simplePath.exists(_.minion == SceneMinion.Wall)

  override def getUpdatedTargetAndCooldown:(SceneCell, Int) = {
    val randomMove = FlightyMove.ActiveMoves(this.requestRecord.timeElapsed % FlightyMove.ActiveMoves.size)
    val targetCell = this.sceneView.getCell(this.flightyViewState.mainCell, randomMove, 10)
    targetCell -> this.flightyViewState.mainCell.getMoves(targetCell)
  }

  override def enact: Boolean = true
}