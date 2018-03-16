package com.partridgetech.command.move.choices

import com.partridgetech.common.{FlightyMove, FlightyViewState, SceneMinion}
import com.partridgetech.navigation.SceneCell
import com.partridgetech.pathfinding.AStarPathFinder

import scala.util.Random

class SummonsMove (flightyViewState: FlightyViewState) extends BaseMove(flightyViewState) {

  // Naive path between cell and master bot.
  val simplePath: List[SceneCell] = this.sceneView.getCellsBetween(this.flightyViewState.mainCell, this.flightyViewState.masterLocation)


  override def updateTargetCell:Boolean = super.updateTargetCell || !this.simplePath.exists(SceneMinion.Wall==_.minion)

  override def getUpdatedTargetAndCooldown: (SceneCell, Int) = {

    val targetCells: Seq[SceneCell] = new AStarPathFinder(this.sceneView, this.flightyViewState.mainCell, this.flightyViewState.masterLocation).getCellPath

    if (targetCells.size < 10 && this.simplePath.exists(_.minion == SceneMinion.Wall)) {
      val directionAwayFromWall = this.flightyViewState.getMainCellMoveFrom(simplePath.filter(_.minion == SceneMinion.Wall).head)
      val cellAwayFromWall = this.sceneView.getCell(this.flightyViewState.mainCell, directionAwayFromWall, 10)
      (cellAwayFromWall, 10)
    }
    else targetCells.last -> targetCells.size * 2
  }

    /**
      * Returns list of cells to the master.
      *
      * @return list of cells
      */
    def getPathToMaster: List[SceneCell] = {
      // Get path to master.
      new AStarPathFinder(this.flightyViewState.sceneView, this.flightyViewState.mainCell, this.flightyViewState.masterLocation).getCellPath
    }


    def getPathToInvertedMaster: List[SceneCell] = {
      val alternateMasterTarget: (Int, Int) = -this.requestRecord.relativeMasterLocation._1 -> -this.requestRecord.relativeMasterLocation._2
      new AStarPathFinder(this.flightyViewState.sceneView, this.flightyViewState.mainCell, this.sceneView.getCell(alternateMasterTarget._1, alternateMasterTarget._2)).getCellPath
    }

    override def enact: Boolean = true
  }

