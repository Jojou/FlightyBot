package com.partridgetech.model

import com.partridgetech.attribute.InputAttributeConverter
import com.partridgetech.common.attribute.Attribute

/**
  * Record contains data sent from server
  *
  * @param welcomeRequestRecord data from initial server request
  * @param properties properties
  */
class GoodbyeRequestRecord(welcomeRequestRecord: WelcomeRequestRecord, properties: Map[String, String]) extends RequestRecord {

  override def requestResponse: String = ""

  val energy: Int = InputAttributeConverter.getInputAttribute(InputAttributeConverter.IntegerConverter,
    properties.get(Attribute.ENERGY.getName))


  println("Final energy of FlightyBot = " + this.energy)


}