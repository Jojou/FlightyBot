package com.partridgetech.command.essence.choices


import com.partridgetech.attribute.OutputCommand
import com.partridgetech.common.{FlightyChoice, FlightyIntention, FlightyMove, FlightyViewState}
import com.partridgetech.navigation.SceneCell

/**
  * Provides ability to save custom information to output.
  *
  * @param flightyViewState the view state of the entity
  * @param moveCommand the move of the entity
  */
class BaseEssence(flightyViewState: FlightyViewState, intention: FlightyIntention, moveCommand:OutputCommand.Move) extends FlightyChoice[OutputCommand](flightyViewState) {

  /**
    * Get output command of essence.
    *
    * @return OutputCommand
    */
  def getOutcome: OutputCommand = {

    val moveHistory = this.flightyViewState.requestRecord.moveHistory.+:(this.moveCommand.move).take(20)
    val targetCell: SceneCell = this.moveCommand.targetCell
    val cooldown:Int = this.moveCommand.cooldown

    OutputCommand.Set(
      this.flightyViewState.requestRecord.timeElapsed + 1,
      moveHistory,
      this.intention,
      targetCell,
      Math.max(0, cooldown))
  }


  /**
    * Returns tuple 2 of target cell and when target cell should be recalculated.
    *
    * @return target cell, time (seconds)
    */
  def getUpdatedTargetCellCooldown: (SceneCell, Int) = {this.flightyViewState.mainCell -> 0}

  /**
    * Returns boolean indicating whether this essence should be selected.
    *
    * @return boolean
    */
  def enact: Boolean = true
}