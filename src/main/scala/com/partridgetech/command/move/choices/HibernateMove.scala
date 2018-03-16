package com.partridgetech.command.move.choices

import com.partridgetech.common.FlightyViewState
import com.partridgetech.navigation.SceneCell

class HibernateMove(flightyViewState: FlightyViewState) extends BaseMove(flightyViewState) {


  val tooCloseToWall:Boolean = this.flightyViewState.closestWall.isDefined &&
      this.flightyViewState.mainCell.getMoves(this.flightyViewState.closestWall.get) < HibernateMove.MIN_HIBERNATE_CLOSEST_DISTANCE

  override def preventRepeatingMoves = false

  override def enact: Boolean = this.flightyViewState.closestFriendlySlave.isDefined &&
    this.flightyViewState.mainCell.getMoves(this.flightyViewState.closestFriendlySlave.get) > HibernateMove.MIN_HIBERNATE_CLOSEST_DISTANCE &&
    !this.tooCloseToWall

  override def getUpdatedTargetAndCooldown:(SceneCell, Int) = {
    this.flightyViewState.mainCell -> this.requestRecord.targetCooldown
  }
}


object HibernateMove {
  val MIN_HIBERNATE_CLOSEST_DISTANCE = 5
  val MIN_FRIENDLY_SLAVES = 2
}