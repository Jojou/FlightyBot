package com.partridgetech.utility

import com.partridgetech.model.WelcomeRequestRecord

/**
  * This object contains the only class member var in the entire application.  It simply contains a welcome request
  * record, which contains game specific information about the Scalatron match in progress.
  */
object WelcomeRequestHolder {

  private var _record: WelcomeRequestRecord = _

  def record: WelcomeRequestRecord = _record

  def createWelcomeRequestRecord(keyValuePairs: Map[String, String]): WelcomeRequestRecord = {
    this._record = new WelcomeRequestRecord(keyValuePairs)
    this.record
  }
}