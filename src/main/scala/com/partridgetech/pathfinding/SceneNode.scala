package com.partridgetech.pathfinding

import com.partridgetech.navigation.SceneCell

/**
  * Contains information for building a path as required by A * search algorithm.
  *
  * @param ghfCell parent cell, distance from start, distance from end
  * @param nodeParent parent node
  */
class SceneNode(ghfCell: (SceneCell, Int, Int), var nodeParent: Option[SceneNode]) {
  /** The scene cell this node relates too. */
  val sceneCell: SceneCell = ghfCell._1
  /** g value - distance from start. */
  val g: Int = ghfCell._2
  /** h value - distance from end. */
  val h: Int = ghfCell._3
  /** f value - combination of h + f. */
  val f: Int = g + h

  /**
    * Traverse through parent nodes forming a collection
    *
    * @return Seq of cells formed of all parents
    */
  def getCellPath: Seq[SceneCell] = this.getCellsToParent(Seq[SceneCell](this.sceneCell))

  /**
    * Checks if node has parent in which case it is root and returns after reversing list.  Otherwise calls parent node.
    *
    * @param sceneCells scene cells of nodes
    * @return seq of scene cells
    */
  private def getCellsToParent(sceneCells: Seq[SceneCell]): Seq[SceneCell] =
    if (this.nodeParent.isDefined)
      this.nodeParent.get.getCellsToParent(sceneCells :+ nodeParent.get.sceneCell)
    else
      sceneCells.reverse
}
