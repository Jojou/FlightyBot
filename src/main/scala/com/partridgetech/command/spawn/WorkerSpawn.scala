package com.partridgetech.command.spawn

import com.partridgetech.attribute.OutputCommand
import com.partridgetech.command.spawn.choices.{BaseSpawn, SlaveScoutSpawn}
import com.partridgetech.common.FlightyChoice
import com.partridgetech.mech.FlightyBot

import scala.collection.mutable.ArrayBuffer

trait WorkerSpawn extends FlightyBot {

  abstract override def getOutputCommands(outputCommands: ArrayBuffer[OutputCommand]): ArrayBuffer[OutputCommand] = {
    val choice = this.getChoices(this).find(choice => choice.enact)
    if (choice.isDefined) outputCommands.+=(choice.get.getOutcome)
    super.getOutputCommands(outputCommands)
  }

  def getChoices(parameter: WorkerSpawn): List[FlightyChoice[OutputCommand]] = {
    List(new SlaveScoutSpawn(this.flightyViewState))
  }
}