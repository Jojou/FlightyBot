package com.partridgetech.pathfinding

import com.partridgetech.common.SceneMinion
import com.partridgetech.navigation.{SceneCell, SceneView}

/**
  * Uses a start path finding to located cell sequence to end cell.  If complete path cannot be found then a partial list
  * is returned.
  *
  * @param sceneView information on cells in map
  * @param startCell start location
  * @param endCell end loation
  */

class AStarPathFinder(val sceneView: SceneView, val startCell: SceneCell, val endCell: SceneCell) {


  /**
    * Gets list of cells (not including start) to end cell (included).
    *
    * @return list of cells
    */
  def getCellPath: List[SceneCell] = {
    this.getPathToEndCell(new scala.collection.mutable.HashSet[SceneNode].+(this.createNode(this.startCell)), new scala.collection.mutable.HashSet[SceneNode]())
  }

  /**
    * Provides list of cells required to solve maze.
    *
    * @param openList candidate list
    * @param closedList completed list
    * @return List[SceneCell] cells
    */
  private def getPathToEndCell(openList: scala.collection.mutable.Set[SceneNode], closedList: scala.collection.mutable.Set[SceneNode]): List[SceneCell] = {

    if (openList.isEmpty)
      closedList.head.getCellPath.toList.filterNot(_ == this.startCell).::(this.endCell)
    else {
      val lowestOpenCell: SceneNode = openList.minBy(_.f)
      // Move cell from open list to closed list.
      closedList.add(lowestOpenCell)
      openList.remove(lowestOpenCell)
      // Get adjacent cells to closed list.
      val adjacentCells = this.sceneView.getAdjacentCells(lowestOpenCell.sceneCell).filterNot(cell => SceneMinion.getBlockingMinions(false).contains(cell.minion))

      // Check if maze is completed.
      if (adjacentCells.contains(endCell))
        lowestOpenCell.getCellPath.toList.filterNot(_ == this.startCell).::(this.endCell)
      else {
        adjacentCells.foreach(cell => {
          // Get g value of adjacent maze cell.
          val updatedGValue = cell.getMoves(startCell)
          // Get closed node if exists.
          val closedResult = closedList.find(_.sceneCell == cell)
          // Get open node if exists.
          val openResult = openList.find(_.sceneCell == cell)

          if (closedResult.isDefined && updatedGValue < closedResult.get.g) {
            closedList.remove(closedResult.get)
            closedList.add(this.createNode(cell, lowestOpenCell))
          }
          else if (openResult.isDefined && updatedGValue < openResult.get.g) {
            openList.remove(closedResult.get)
            openList.add(this.createNode(cell, lowestOpenCell))
          }
          else if (closedResult.isEmpty && openResult.isEmpty)
            openList.add(this.createNode(cell, lowestOpenCell))
        })
        this.getPathToEndCell(openList, closedList)
      }
    }
  }

  /**
    * Convenience mechanism for creating a SceneNode.
    *
    * @param cell to create node for
    * @return SceneNode
    */
  private def createNode(cell: SceneCell): SceneNode = {
    new SceneNode((cell, cell.getMoves(startCell), cell.getMoves(endCell)), Option.empty)
  }

  /**
    * Convenience mechanism for creating a SceneNode.
    *
    * @param cell   to create node for
    * @param parent node
    * @return SceneNode
    */
  private def createNode(cell: SceneCell, parent: SceneNode): SceneNode = {
    new SceneNode((cell, cell.getMoves(startCell), cell.getMoves(endCell)), Option.apply(parent))
  }
}