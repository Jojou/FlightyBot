package com.partridgetech.command.spawn

import com.partridgetech.attribute.OutputCommand
import com.partridgetech.command.spawn.choices._
import com.partridgetech.common.FlightyChoice
import com.partridgetech.mech.FlightyBot

import scala.collection.mutable.ArrayBuffer

trait MasterSpawn extends FlightyBot {

  abstract override def getOutputCommands(outputCommands: ArrayBuffer[OutputCommand]): ArrayBuffer[OutputCommand] = {
    val choice: Option[FlightyChoice[OutputCommand]] = this.getChoices(this).find(choice => choice.enact)

    if (choice.isDefined) outputCommands.+=(choice.get.getOutcome)
    super.getOutputCommands(outputCommands)
  }

  def getChoices(parameter: MasterSpawn): List[FlightyChoice[OutputCommand]] = {
    List(
      new AttackSpawn(this.flightyViewState),
      new MasterScoutSpawn(this.flightyViewState)
    )
  }
}