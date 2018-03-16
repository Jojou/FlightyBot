package com.partridgetech.command.status.choices

import com.partridgetech.attribute.OutputCommand
import com.partridgetech.common.{FlightyChoice, FlightyIntention, FlightyViewState}

class AlwaysOn(requestRecord: FlightyViewState, intention: FlightyIntention) extends
  FlightyChoice[OutputCommand](requestRecord) {

  override def getOutcome: OutputCommand = {
    OutputCommand.Status(intention.toString)
  }

  def enact: Boolean = false
}