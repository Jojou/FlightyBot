package com.partridgetech.command.move

import com.partridgetech.attribute.OutputCommand
import com.partridgetech.command.essence.choices.HibernateMove
import com.partridgetech.command.move.choices._
import com.partridgetech.common.FlightyChoice
import com.partridgetech.mech.FlightyBot
import com.partridgetech.navigation.SceneCell

import scala.collection.mutable.ArrayBuffer

trait SlaveExploreMove extends FlightyBot {

  abstract override def getOutputCommands(outputCommands: ArrayBuffer[OutputCommand]): ArrayBuffer[OutputCommand] = {
    val choice = this.getChoices(this).find(choice => choice.enact)
    if (choice.isDefined) outputCommands.+=(choice.get.getOutcome)
    super.getOutputCommands(outputCommands)
  }

  def getChoices(parameter: SlaveExploreMove): List[FlightyChoice[OutputCommand]] = {
    List(
      new ForagerMove(this.flightyViewState),
      new BlastRadiusAttackMove(this.flightyViewState),
      new BlastRadiusEvadeMove(this.flightyViewState),
      new HibernateMove(this.flightyViewState),
      new LonerMove(this.flightyViewState),
      new ExploreMove(this.flightyViewState))
  }
}