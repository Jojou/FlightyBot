package com.partridgetech.attribute

import com.partridgetech.common.{FlightyIntention, FlightyMove}
import com.partridgetech.navigation.SceneCell

/**
  * Provides ability to create output commands; and output header and the relevent attributes.  Several of these may
  * be recieved at the end of each bot turn.
  *
  * @param outputHeader type of action
  * @param outputAttributes relevent properties
  */
class OutputCommand(val outputHeader: OutputHeader.Value, val outputAttributes: OutputAttribute*) {

  @Override
  override def toString(): String = {

    val commandBuilder = StringBuilder.newBuilder
    commandBuilder.append(outputHeader.toString).append(
      OutputCommand.LEFT_BRACKET)
    this.outputAttributes.foreach(outputAttribute => commandBuilder.append(outputAttribute.toString()).append(','))
    commandBuilder.setLength(commandBuilder.length - 1)
    commandBuilder.append(OutputCommand.RIGHT_BRACKET)
    commandBuilder.toString()
  }
}

object OutputCommand {

  val LEFT_BRACKET = '('
  val RIGHT_BRACKET = ')'

  case class Move(move: FlightyMove, targetCell:SceneCell, cooldown:Int) extends OutputCommand(
    OutputHeader.Move, OutputAttribute.Direction(move.toString()))

  case class Spawn(move: FlightyMove, name: String, energy: Int, intention: FlightyIntention, age: Int,
                   orbitPosition: Int, targetCell: SceneCell, targetCooldown: Int, timeElapsed:Int) extends OutputCommand(OutputHeader.Spawn,
    OutputAttribute.Direction(move.toString()),
    OutputAttribute.Name(name),
    OutputAttribute.Energy(String.valueOf(energy)),
    OutputAttribute.Intention(intention.toString),
    OutputAttribute.TimeCreated(String.valueOf(age)),
    OutputAttribute.OrbitPosition(String.valueOf(orbitPosition)),
    OutputAttribute.TargetCell(targetCell.x + ":" + targetCell.y),
    OutputAttribute.TargetCoolDown(String.valueOf(targetCooldown)),
    OutputAttribute.TimeElapsed(String.valueOf(timeElapsed)))

  case class Explode(size: Int) extends OutputCommand(OutputHeader.Explode, OutputAttribute.Size(String
    .valueOf(size)))

  case class Set(timeElapsed: Int, moveHistory: List[FlightyMove], intention: FlightyIntention, targetCell: SceneCell,
                 targetCooldown: Int) extends OutputCommand(OutputHeader.Set,
    OutputAttribute.TimeElapsed(String.valueOf(timeElapsed)),
    OutputAttribute.MoveHistory(moveHistory.foldLeft(StringBuilder.newBuilder)((builder: StringBuilder,
                                                                                move: FlightyMove) => builder.append(move.toString + '@')).dropRight(1).toString),
    OutputAttribute.Intention(intention.toString),
    OutputAttribute.TargetCell(targetCell.x + ":" + targetCell.y),
    OutputAttribute.TargetCoolDown(String.valueOf(targetCooldown)))

  case class Log(direction: String) extends OutputCommand(OutputHeader.Log, OutputAttribute.Text(direction))

  case class Say(direction: String) extends OutputCommand(OutputHeader.Say, OutputAttribute.Text(direction))

  case class Status(direction: String) extends OutputCommand(OutputHeader.Status, OutputAttribute.Text(direction))

}