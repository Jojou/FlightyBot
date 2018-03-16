package com.partridgetech.command.essence

import com.partridgetech.attribute.OutputCommand
import com.partridgetech.command.essence.choices.BaseEssence
import com.partridgetech.common.FlightyChoice
import com.partridgetech.mech.FlightyBot

import scala.collection.mutable.ArrayBuffer

trait BotEssence extends FlightyBot {

  abstract override def getOutputCommands(outputCommands: ArrayBuffer[OutputCommand]): ArrayBuffer[OutputCommand] = {

    val moveCommand:OutputCommand.Move = this.findBotCommand(outputCommands, classOf[OutputCommand.Move]).get

    val choice = this.getChoices(this, moveCommand).find(choice => choice.enact)
    if (choice.isDefined) outputCommands.+=(choice.get.getOutcome)
    super.getOutputCommands(outputCommands)
  }

  def getChoices(parameter: BotEssence, moveCommand:OutputCommand.Move): List[FlightyChoice[OutputCommand]] = {
    List(
      new BaseEssence(this.flightyViewState, this.intention, moveCommand)
    )
  }
}