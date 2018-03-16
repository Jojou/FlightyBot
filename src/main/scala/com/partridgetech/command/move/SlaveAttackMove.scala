package com.partridgetech.command.move

import com.partridgetech.attribute.OutputCommand
import com.partridgetech.command.move.choices._
import com.partridgetech.common.FlightyChoice
import com.partridgetech.mech.FlightyBot

import scala.collection.mutable.ArrayBuffer

trait SlaveAttackMove extends FlightyBot {

  abstract override def getOutputCommands(outputCommands: ArrayBuffer[OutputCommand]): ArrayBuffer[OutputCommand] = {
    val choice = this.getChoices(this).find(choice => choice.enact)
    if (choice.isDefined) outputCommands.+=(choice.get.getOutcome)
    super.getOutputCommands(outputCommands)
  }

  def getChoices(parameter: SlaveAttackMove): List[FlightyChoice[OutputCommand]] = {
    List(new EnemyMasterMove(this.flightyViewState),
      new StillMove(this.flightyViewState))
  }
}