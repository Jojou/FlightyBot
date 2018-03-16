package com.partridgetech.command.move

import com.partridgetech.attribute.OutputCommand
import com.partridgetech.command.move.choices.{ExploreMove, LurkMove, SwarmMove}
import com.partridgetech.common.FlightyChoice
import com.partridgetech.mech.FlightyBot

import scala.collection.mutable.ArrayBuffer

trait CautiousMasterMove extends FlightyBot {

  abstract override def getOutputCommands(outputCommands: ArrayBuffer[OutputCommand]): ArrayBuffer[OutputCommand] = {
    val choice = this.getChoices(this).find(choice => choice.enact)
    if (choice.isDefined) outputCommands.+=(choice.get.getOutcome)
    super.getOutputCommands(outputCommands)
  }

  def getChoices(parameter: CautiousMasterMove): List[FlightyChoice[OutputCommand]] = {
    List(
      new LurkMove(this.flightyViewState),
      new SwarmMove(this.flightyViewState),
      new ExploreMove(this.flightyViewState))
  }
}