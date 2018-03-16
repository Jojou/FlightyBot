package com.partridgetech.model

import com.partridgetech.attribute.InputAttributeConverter
import com.partridgetech.common.attribute.Attribute

class WelcomeRequestRecord(properties: Map[String, String]) extends RequestRecord {

  override def requestResponse: String = ""

  val timeLimit: Int = InputAttributeConverter.getInputAttribute(InputAttributeConverter.IntegerConverter,
    properties.get(Attribute.TIME_LIMIT.getName))
  val name: String = InputAttributeConverter.getInputAttribute(InputAttributeConverter.StringConverter,
    properties.get(Attribute.NAME.getName))
  val round: Int = InputAttributeConverter.getInputAttribute(InputAttributeConverter.IntegerConverter,
    properties.get(Attribute.ROUND.getName))
  val maxSlaves: Int = InputAttributeConverter.getInputAttribute(InputAttributeConverter.IntegerConverter,
    properties.get(Attribute.MAX_SLAVES.getName))
}