package com.partridgetech.command.explode

import com.partridgetech.attribute.OutputCommand
import com.partridgetech.command.explode.choices.EfficientExplode
import com.partridgetech.common.FlightyChoice
import com.partridgetech.mech.FlightyBot

import scala.collection.mutable.ArrayBuffer

trait OpportunisticDetonation extends FlightyBot {

  abstract override def getOutputCommands(outputCommands: ArrayBuffer[OutputCommand]): ArrayBuffer[OutputCommand] = {
    val choice = this.getChoices(this).find(choice => choice.enact)
    if (choice.isDefined) outputCommands.+=(choice.get.getOutcome)
    super.getOutputCommands(outputCommands)
  }


  def getChoices(parameter: OpportunisticDetonation): List[FlightyChoice[OutputCommand]] = {
    List(new EfficientExplode(this.flightyViewState))
  }
}
