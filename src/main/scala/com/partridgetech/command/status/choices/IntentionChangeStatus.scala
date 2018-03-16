package com.partridgetech.command.status.choices

import com.partridgetech.attribute.OutputCommand
import com.partridgetech.common.{FlightyChoice, FlightyIntention, FlightyViewState}

class IntentionChangeStatus(flightyViewState: FlightyViewState, intention: FlightyIntention) extends
  FlightyChoice[OutputCommand](flightyViewState) {

  override def getOutcome: OutputCommand = {
    OutputCommand.Status(intention.toString.charAt(0).toString)
  }

  def enact: Boolean = {
    this.requestRecord.getBotAge == 1 || intention != this.requestRecord.intention
  }
}