package com.partridgetech.command.move.choices

import com.partridgetech.common.FlightyViewState
import com.partridgetech.navigation.SceneCell

class EnemyMasterMove(flightyViewState: FlightyViewState) extends BaseMove(flightyViewState) {


  override def updateTargetCell:Boolean = this.flightyViewState.enemyMaster.isDefined

  override def getUpdatedTargetAndCooldown:(SceneCell, Int) = {
    this.flightyViewState.enemyMaster.get -> this.flightyViewState.mainCell.getMoves(this.flightyViewState.enemyMaster.get)
  }


  override def enact: Boolean = {
    this.flightyViewState.enemyMaster.isDefined || this.requestRecord.targetCooldown != 0
  }
}