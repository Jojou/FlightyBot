package com.partridgetech.command.move.choices

import com.partridgetech.common.FlightyViewState
import com.partridgetech.navigation.SceneCell

class ForagerMove(flightyViewState: FlightyViewState) extends BaseMove(flightyViewState) {

  // For consumable cell get cells with closest distance
  val cellsClosestTo: Seq[(SceneCell, Int, Seq[SceneCell])] = this.flightyViewState.sceneView.getClosestCellsDistances(
    this.flightyViewState.consumableCells, this.flightyViewState.allBotCells).filter(entry => entry._3.contains(this.flightyViewState.mainCell))

  val closestConsumable: Option[SceneCell] = {
    if (cellsClosestTo.isEmpty)
      Option.empty
    else {
      // Find earliest cell that after tieBreaker is closest to or does not contain another friendly cell
      this.cellsClosestTo.toList.sortBy(entry => entry._2).find(
        result => result._3.filterNot(_.equals(this.flightyViewState.mainCell)).forall(cell =>
          !(this.flightyViewState.friendlySlaves.contains(cell) && !this.flightyViewState.mainCell.isCellCloserToThan(result._1, cell))))
        .map(_._1)
    }
  }
  override def enact: Boolean = this.closestConsumable.isDefined

  override def getUpdatedTargetAndCooldown:(SceneCell, Int) = {
    this.closestConsumable.get -> this.requestRecord.targetCooldown
  }
}
