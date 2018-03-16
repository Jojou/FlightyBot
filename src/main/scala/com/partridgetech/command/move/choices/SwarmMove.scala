package com.partridgetech.command.move.choices

import com.partridgetech.attribute.OutputCommand
import com.partridgetech.common.{FlightyMove, FlightyViewState, SceneMinion}
import com.partridgetech.navigation.SceneCell

class SwarmMove (flightyViewState: FlightyViewState) extends BaseMove(flightyViewState) {

  override def getUpdatedTargetAndCooldown: (SceneCell, Int) = {

    val degreesOfFriendlySlaves: Seq[Double] = this.flightyViewState.friendlySlaves.map(cell => this.flightyViewState.mainCell.getDegreesTo(cell))

    val movesWithCount: Seq[(FlightyMove, Int)] = FlightyMove.ActiveMoves.map(activeMove => activeMove -> degreesOfFriendlySlaves
      .count(activeMove.isAngleWithinMoveDegrees(_)))

    this.sceneView.getCell(this.flightyViewState.mainCell, movesWithCount.maxBy(_._2)._1) -> 0
  }

  override def enact: Boolean = true
}