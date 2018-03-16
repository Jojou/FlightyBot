package com.partridgetech

import com.partridgetech.common.InputHeader
import com.partridgetech.model.{GoodbyeRequestRecord, ReactRequestRecord, RequestRecord}
import com.partridgetech.utility.WelcomeRequestHolder


/**
  * This receives input from Scalatron and returns response from bot.
  */

object ControlFunction {

  /**
    * Refer to Scalatron documentation on make up of String inputted and outputted.  Returns bot's response.
    *
    * @param input from Scalatron
    * @return from FlightBot
    */
  def respond(input: String): String = {

    val inputHeader = InputHeader.Default.getInputHeader(input)

    val requestRecord: RequestRecord = inputHeader match {
      case InputHeader.Welcome => WelcomeRequestHolder.createWelcomeRequestRecord(this.getKeyValuesToList(input))
      case InputHeader.React => new ReactRequestRecord(WelcomeRequestHolder.record, this.getKeyValuesToList(input))
      case InputHeader.Goodbye => new GoodbyeRequestRecord(WelcomeRequestHolder.record, this.getKeyValuesToList(input))
      case _ => WelcomeRequestHolder.record
    }

    requestRecord.requestResponse
  }

  /**
    * Helper method to convert string into key pairs.
    *
    * @param inputString comma seperated list
    * @return map of key-values
    */
  def getKeyValuesToList(inputString: String): Map[String, String] = {
    // Remove trailing and preceding brackets ( )
    val keysAndValues: String = inputString.split('(').apply(1).split(')').apply(0)
    keysAndValues.split(',').map(keyVal => keyVal.split('=').apply(0) -> keyVal.split('=').apply(1)).toMap
  }
}
