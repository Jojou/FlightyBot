package com.partridgetech.command.move.choices

import com.partridgetech.common.FlightyViewState
import com.partridgetech.navigation.SceneCell

class StillMove(flightyViewState: FlightyViewState) extends BaseMove(flightyViewState) {

  override def enact: Boolean = true

  override def getUpdatedTargetAndCooldown:(SceneCell, Int) = this.flightyViewState.mainCell -> 0

}