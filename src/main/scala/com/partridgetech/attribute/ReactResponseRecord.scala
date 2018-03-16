package com.partridgetech.attribute

/**
  * This record is returned at the end of each round to provide a properly formed string of the bot's actions.
  *
  * @param outputCommands List of actions bots would like tot take
  */
case class ReactResponseRecord(outputCommands: List[OutputCommand]) {

  @Override
  override def toString(): String = {
    val reactBuilder = StringBuilder.newBuilder
    outputCommands.foreach(command => reactBuilder.append(command.toString + ReactResponseRecord.PIPE))
    reactBuilder.dropRight(1).toString()
  }
}

object ReactResponseRecord {
  val PIPE: Char = '|'
}