package com.partridgetech.command.move

import com.partridgetech.attribute.OutputCommand
import com.partridgetech.command.move.choices.{ForagerMove, SummonsMove}
import com.partridgetech.common.FlightyChoice
import com.partridgetech.mech.FlightyBot
import com.partridgetech.navigation.SceneCell

import scala.collection.mutable.ArrayBuffer

trait SlaveReturnMove extends FlightyBot {

  abstract override def getOutputCommands(outputCommands: ArrayBuffer[OutputCommand]): ArrayBuffer[OutputCommand] = {

    val choice = this.getChoices(this).find(choice => choice.enact)

    if (choice.isDefined) outputCommands.+=(choice.get.getOutcome)
    super.getOutputCommands(outputCommands)
  }

  def getChoices(parameter: SlaveReturnMove): List[FlightyChoice[OutputCommand]] = {
    List(
      new ForagerMove(this.flightyViewState),
      new SummonsMove(this.flightyViewState))
  }
}