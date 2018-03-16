package com.partridgetech.mech

import com.partridgetech.attribute.{OutputCommand, ReactResponseRecord}
import com.partridgetech.common.{FlightyIntention, FlightyMove, FlightyViewState}
import com.partridgetech.navigation.SceneCell

import scala.collection.mutable.ArrayBuffer

abstract class FlightyBot(val intention: FlightyIntention, protected val flightyViewState: FlightyViewState) {

  def getResponse: ReactResponseRecord = {
    new ReactResponseRecord(this.getOutputCommands(ArrayBuffer()).toList)
  }

  def getOutputCommands(outputCommands: ArrayBuffer[OutputCommand]): ArrayBuffer[OutputCommand] = outputCommands

  def findBotCommand[T <: OutputCommand](botCommands: Seq[OutputCommand], clazz: Class[T]): Option[T] = {
    botCommands.find(command => command.getClass.isAssignableFrom(clazz)).map(command => command.asInstanceOf[T])
  }
}