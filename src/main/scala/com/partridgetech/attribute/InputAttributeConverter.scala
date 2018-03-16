package com.partridgetech.attribute

import com.partridgetech.common.{FlightyIntention, FlightyMove}

/**
  * Each turn a number of properties need to be converted from string to a more useful data type.
  *
  * @tparam T the type that will be returned after conversion
  */

abstract class InputAttributeConverter[T] {

  def getValue(input: String): T

  def getDefaultValue: T
}

object InputAttributeConverter {

  case object StringConverter extends InputAttributeConverter[String] {
    def getValue(input: String): String = {
      input
    }

    def getDefaultValue: String = {
      ""
    }
  }

  case object IntegerConverter extends InputAttributeConverter[Int] {
    def getValue(input: String): Int = {
      input.toInt
    }

    def getDefaultValue: Int = {
      0
    }
  }

  case object IntentionConverter extends InputAttributeConverter[FlightyIntention] {
    def getValue(input: String): FlightyIntention = {
      FlightyIntention.getIntention(input)
    }

    def getDefaultValue: FlightyIntention = {
      FlightyIntention.RapidExpansion
    }
  }

  case object MoveConverter extends InputAttributeConverter[FlightyMove] {
    def getValue(input: String): FlightyMove = {
      val splitItems = input.split(":")
      return FlightyMove.getMove(splitItems(0).toInt, splitItems(1).toInt)
    }

    def getDefaultValue: FlightyMove = {
      FlightyMove.RIGHT_DOWN
    }
  }

  case object MoveHistoryConverter extends InputAttributeConverter[List[FlightyMove]] {
    def getValue(input: String): List[FlightyMove] = {
      input.split("@").map(splitItem => MoveConverter.getValue(splitItem)).toList
    }

    def getDefaultValue: List[FlightyMove] = {
      List(FlightyMove.CENTER_CENTER)
    }
  }

  case object SceneCellCoordinateConverter extends InputAttributeConverter[(Int, Int)] {
    def getValue(input: String): (Int, Int) = {
      val splitItems = input.split(":")
      return (splitItems(0).toInt, splitItems(1).toInt)
    }

    def getDefaultValue: (Int, Int) = {
      (0, 0)
    }
  }

  def getInputAttribute[T](inputAttributeConverter: InputAttributeConverter[T], inputValue: Option[String]): T = {
    if (inputValue.isDefined)
      inputAttributeConverter.getValue(inputValue.get)
    else
      inputAttributeConverter.getDefaultValue
  }
}