package com.partridgetech.command.move.choices

import com.partridgetech.common.{FlightyMove, FlightyViewState, SceneMinion}
import com.partridgetech.navigation.SceneCell


class LonerMove (flightyViewState: FlightyViewState) extends BaseMove(flightyViewState) {

  val simplePath: List[SceneCell] = this.sceneView.getCellsBetween(this.flightyViewState.mainCell, this.requestRecord.targetCell)

  override def getUpdatedTargetAndCooldown:(SceneCell, Int) = {
    val degreesOfFriendlySlavesWalls: Seq[Double] = this.flightyViewState.friendlySlaves.++(this.flightyViewState.walls).map(cell => this.flightyViewState.mainCell.getDegreesTo(cell))

    val degreesOfEmptyCells:Seq[Double] = this.flightyViewState.emptyCells.++(this.flightyViewState.enemySlaves).map(cell => this.flightyViewState.mainCell.getDegreesTo(cell))

    val movesWithCount: Seq[(FlightyMove, Int)] = FlightyMove.ActiveMoves.map(activeMove => activeMove -> (
      degreesOfEmptyCells.count(activeMove.isAngleWithinMoveDegrees(_)) - degreesOfFriendlySlavesWalls.count(
        activeMove.isAngleWithinMoveDegrees(_))))

    val directionWithLeastFriendlies:FlightyMove = movesWithCount.maxBy(_._2)._1

    this.sceneView.getCell(this.flightyViewState.mainCell, directionWithLeastFriendlies, 10) -> 10
  }

  override def enact: Boolean = this.flightyViewState.friendlySlaves.size > 3 || simplePath.exists(_.minion == SceneMinion.Wall)
}