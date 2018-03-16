package com.partridgetech.command.status

import com.partridgetech.attribute.OutputCommand
import com.partridgetech.command.status.choices.IntentionChangeStatus
import com.partridgetech.common.{FlightyChoice, FlightyIntention}
import com.partridgetech.mech.FlightyBot

import scala.collection.mutable.ArrayBuffer

trait StatusReport extends FlightyBot {

  abstract override def getOutputCommands(outputCommands: ArrayBuffer[OutputCommand]): ArrayBuffer[OutputCommand] = {

    val updatedIntention = this.findBotCommand(outputCommands, classOf[OutputCommand.Set]).get.intention
    val choice = this.getChoices(this, updatedIntention).find(choice => choice.enact)
    if (choice.isDefined) outputCommands.+=(choice.get.getOutcome)
    super.getOutputCommands(outputCommands)
  }

  def getChoices(parameter: StatusReport, updatedIntention:FlightyIntention): List[FlightyChoice[OutputCommand]] = {
    List(new IntentionChangeStatus(this.flightyViewState, updatedIntention))
  }
}