package com.partridgetech.command.move.choices

import com.partridgetech.attribute.OutputCommand
import com.partridgetech.common.FlightyViewState
import com.partridgetech.navigation.SceneCell

class LurkMove(flightyViewState: FlightyViewState) extends BaseMove(flightyViewState) {

  override def enact: Boolean = {
    this.flightyViewState.closestEnemySlave.isDefined && this.flightyViewState.mainCell.getMoves(this.flightyViewState.closestEnemySlave.get) > LurkMove.MinLurkDistance &&
      this.flightyViewState.mainCell.getMoves(this.flightyViewState.closestEnemySlave.get) < LurkMove.LurkThreshold
  }

  override def getUpdatedTargetAndCooldown:(SceneCell, Int) = {
    (this.sceneView.getCell(this.flightyViewState.mainCell, this.flightyViewState.getMainCellMoveFrom(this.flightyViewState.closestEnemySlave.get)), 0)
  }
}

object LurkMove {
  val MinLurkDistance = 10
  val LurkThreshold = 12
}
