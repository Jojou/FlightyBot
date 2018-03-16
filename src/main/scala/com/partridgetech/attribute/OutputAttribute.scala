package com.partridgetech.attribute

import com.partridgetech.common.attribute.Attribute


class OutputAttribute(attribute: Attribute, value: String) {

  @Override
  override def toString(): String = {
    StringBuilder.newBuilder
      .append(this.attribute.getName)
      .append(OutputAttribute.EQUALS)
      .append(value).toString()
  }
}

object OutputAttribute {
  val EQUALS: Char = '='

  case class Direction(value: String) extends OutputAttribute(Attribute.DIRECTION, value)

  case class Name(value: String) extends OutputAttribute(Attribute.NAME, value)

  case class Energy(value: String) extends OutputAttribute(Attribute.ENERGY, value)

  case class Intention(value: String) extends OutputAttribute(Attribute.INTENTION, value)

  case class CountdownToDefaultMoveChange(value: String) extends OutputAttribute(Attribute.COUNTDOWN_TO_DEFAULT_MOVE_CHANGE, value)

  case class MoveHistory(value: String) extends OutputAttribute(Attribute.MOVE_HISTORY, value)

  case class TimeCreated(value: String) extends OutputAttribute(Attribute.TIME_CREATED, value)

  case class OrbitPosition(value: String) extends OutputAttribute(Attribute.ORBITPOSITION, value)

  case class Size(value: String) extends OutputAttribute(Attribute.SIZE, value)

  case class Text(value: String) extends OutputAttribute(Attribute.TEXT, value)

  case class DefaultMove(value: String) extends OutputAttribute(Attribute.DEFAULT_MOVE, value)

  case class MapMemory(value: String) extends OutputAttribute(Attribute.MAP_MEMORY, value)

  case class ViewDimension(value: String) extends OutputAttribute(Attribute.VIEW_DIMENSION, value)

  case class TargetCell(value: String) extends OutputAttribute(Attribute.TARGET_CELL, value)

  case class TargetCoolDown(value: String) extends OutputAttribute(Attribute.TARGET_COOLDOWN, value)

  case class TimeElapsed(value: String) extends OutputAttribute(Attribute.TIME_ELAPSED, value)

}