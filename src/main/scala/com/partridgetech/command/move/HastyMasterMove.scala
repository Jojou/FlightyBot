package com.partridgetech.command.move

import com.partridgetech.attribute.OutputCommand
import com.partridgetech.command.move.choices.{ExploreMove, ForagerMove, LonerMove, LurkMove}
import com.partridgetech.common.FlightyChoice
import com.partridgetech.mech.FlightyBot

import scala.collection.mutable.ArrayBuffer

trait HastyMasterMove extends FlightyBot {

  abstract override def getOutputCommands(outputCommands: ArrayBuffer[OutputCommand]): ArrayBuffer[OutputCommand] = {
    val choice = this.getChoices(this).find(choice => choice.enact)
    if (choice.isDefined) outputCommands.+=(choice.get.getOutcome)
    super.getOutputCommands(outputCommands)
  }

  def getChoices(parameter: HastyMasterMove): List[FlightyChoice[OutputCommand]] = {
    List(
          new ForagerMove(this.flightyViewState),
          new ExploreMove(this.flightyViewState))
  }
}