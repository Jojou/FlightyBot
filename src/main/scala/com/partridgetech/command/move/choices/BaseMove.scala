package com.partridgetech.command.move.choices

import com.partridgetech.attribute.OutputCommand
import com.partridgetech.common.{FlightyChoice, FlightyViewState, SceneMinion}
import com.partridgetech.navigation.SceneCell

abstract class BaseMove(flightyViewState: FlightyViewState) extends FlightyChoice[OutputCommand](flightyViewState) {

  def updateTargetCell():Boolean = this.requestRecord.targetCooldown == 0

  def getUpdatedTargetAndCooldown:(SceneCell, Int) = this.flightyViewState.mainCell -> 0

  def preventRepeatingMoves = true

  val possibleMoveCells: Seq[SceneCell] = this.sceneView.getAdjacentCells(this.flightyViewState.mainCell)
    .filter(cell => movableMinions.contains(cell.minion))
    .filterNot(cell => this.flightyViewState.lastVisitedCells.contains(cell))
    .:+(this.flightyViewState.mainCell)

  val saferCells:Seq[SceneCell] = {
    if (this.flightyViewState.closestSnorg.isDefined)
      {
        val safeCells = this.possibleMoveCells.filter(cell => cell.getMoves(this.flightyViewState.closestSnorg.get) > BaseMove.SnorgBufferDistance)
        if (safeCells.isEmpty) this.possibleMoveCells else safeCells
      }
    else possibleMoveCells
  }


  val moveShortlist:Seq[SceneCell] = {
    val nonRepeatingMoves: Seq[SceneCell] = this.saferCells.filterNot(this.flightyViewState.lastVisitedCells.contains(_))
    if (nonRepeatingMoves.nonEmpty && this.preventRepeatingMoves) nonRepeatingMoves else this.saferCells
  }

  def movableMinions: Seq[SceneMinion] = {
    val minionSpecific: SceneMinion = {
      if (this.flightyViewState.isMaster) SceneMinion.FriendlySlave else SceneMinion.FriendlyMaster
    }
    Seq(SceneMinion.Empty, SceneMinion.Fluppet, SceneMinion.Zugar, minionSpecific)
  }

  override def getOutcome: OutputCommand = {

    val targetCellAndCooldown:(SceneCell, Int) = {
      if (this.updateTargetCell) this.getUpdatedTargetAndCooldown
      else (this.requestRecord.targetCell, this.requestRecord.targetCooldown - 1)
    }

    val cellToMoveTo =  {
      val closestCells = this.sceneView.getClosestCellsDistance(targetCellAndCooldown._1, this
        .moveShortlist)._2
      closestCells(this.requestRecord.timeElapsed % closestCells.size)
    }

    OutputCommand.Move(this.flightyViewState.mainCell.getMoveTo(cellToMoveTo), targetCellAndCooldown._1, targetCellAndCooldown._2)
  }
}

object BaseMove{

  val BlastRadiusActionDistance = 5
  val SnorgBufferDistance = 2


}