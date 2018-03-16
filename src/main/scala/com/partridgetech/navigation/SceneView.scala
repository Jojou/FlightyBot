package com.partridgetech.navigation

import com.partridgetech.common.{FlightyMove, SceneMinion}
import com.partridgetech.common.SceneMinion._

/**
  * This class is formed by the
  *
  */
class SceneView(val sceneCells: List[SceneCell]) {

  // Total columns in sceneView
  val viewDimensions: Int = math.sqrt(sceneCells.length).toInt

  val cellString: String = sceneCells.foldLeft(StringBuilder.newBuilder)((builder: StringBuilder, cell: SceneCell
                                                                         ) => builder.append(cell.minion.minionChar)).toString()
  val currentCell: SceneCell = this.sceneCells.find(_.minion == SceneMinion.CurrentEntity).get

  def getClosestCellToTarget(targetCell: SceneCell, cells: Seq[SceneCell]): SceneCell = {
    this.getClosestCellsDistance(targetCell, cells)._2.head
  }


  /**
    * Get cell
    *
    * @param xLocation
    * @param yLocation
    * @return
    */
  def getCell(xLocation: Int, yLocation: Int): SceneCell = {
    this.sceneCells(this.viewDimensions * math.max(math.min(this.viewDimensions - 1, yLocation), 0) + math.max(math.min(this.viewDimensions - 1, xLocation), 0))
  }


  /**
    *
    * @param sceneCell
    * @param move
    * @return
    */
  def getCell(sceneCell: SceneCell, move: FlightyMove): SceneCell = {
    this.getCell(sceneCell.x + move.x, sceneCell.y + move.y)
  }


  /**
    *
    * @param sceneCell
    * @param FlightyMove
    * @param times
    * @return
    */
  def getCell(sceneCell: SceneCell, FlightyMove: FlightyMove, times: Int): SceneCell = {
    (1 to times).foldLeft(sceneCell)((currentCell: SceneCell, _: Int) => this.getCell(currentCell, FlightyMove))
  }


  /**
    *
    * @param sceneCell
    * @return
    */
  def getAdjacentCells(sceneCell: SceneCell): Seq[SceneCell] = {
    FlightyMove.ActiveMoves.map(move => this.getCell(sceneCell, move))
  }


  /**
    *
    * @param gameMinion
    * @return
    */
  def getCellsWithMinion(gameMinion: SceneMinion): Seq[SceneCell] = {
    this.getCellsWithMinion(List(gameMinion))
  }


  /**
    *
    * @param gameMinions
    * @return
    */
  def getCellsWithMinion(gameMinions: Seq[SceneMinion]): Seq[SceneCell] = {
    this.sceneCells.filter(sceneCell => gameMinions.contains(sceneCell.minion))
  }

  /**
    *
    * @param targetCell
    * @param otherCells
    * @return
    */
  def getClosestCellsDistance(targetCell: SceneCell, otherCells: Seq[SceneCell]): (Int, Seq[SceneCell]) = {
    if (otherCells.isEmpty) (Integer.MAX_VALUE, Seq.empty)
    else {
      otherCells.foldLeft(Tuple2[Int, List[SceneCell]](Int.MaxValue, List()))((
                                                                                distanceAndCells: (Int, List[SceneCell]), otherCell: SceneCell) => {
        val distance = otherCell.getMoves(targetCell)
        if (distance == distanceAndCells._1) {
          distanceAndCells._1 -> distanceAndCells._2.+:(otherCell)
        }
        else if (distance < distanceAndCells._1) {
          (distance, List(otherCell))
        }
        else {
          distanceAndCells
        }
      })
    }
  }

  /**
    *
    * @param targetCells
    * @param otherCells
    * @return
    */
  def getClosestCellsDistances(targetCells: Seq[SceneCell], otherCells: Seq[SceneCell]): Seq[(SceneCell, Int, Seq[SceneCell])] = {
    targetCells.map(targetCell => {
      val result = this.getClosestCellsDistance(targetCell, otherCells)
      (targetCell, result._1, result._2)
    })
  }


  /**
    *
    * @param sceneCell
    * @param targetCell
    * @return
    */
  def getCellTowards(sceneCell: SceneCell, targetCell: SceneCell): SceneCell = {
    val FlightyMove: FlightyMove = sceneCell.getMoveTo(targetCell)
    this.getCell(sceneCell, FlightyMove)
  }


  /**
    *
    * @param sceneCell
    * @param endCell
    * @return
    */
  def getCellsBetween(sceneCell: SceneCell, endCell: SceneCell): List[SceneCell] = {
    (0 until sceneCell.getMoves(endCell)).foldLeft(List[SceneCell](sceneCell))(
      (routeCells: List[SceneCell], _: Int) => routeCells.:+(this.getCellTowards(
        routeCells.last, endCell))).drop(1)
  }


  /**
    *
    * @param fromCell
    * @param move
    * @return
    */
  def getCellsInDirectionFromCell(fromCell: SceneCell, move: FlightyMove): Seq[SceneCell] = {
    this.getCellsInDirectionFromCell(fromCell, Seq(move)).head._2
  }


  /**
    *
    * @param fromCell
    * @param moves
    * @return
    */
  def getCellsInDirectionFromCell(fromCell: SceneCell, moves: Seq[FlightyMove]): Seq[(FlightyMove, Seq[SceneCell])] = {

    this.sceneCells.foldLeft(moves.map(move => move -> new scala.collection.mutable.ArrayBuffer[SceneCell]()))(
      (moveToCells:Seq[(FlightyMove, scala.collection.mutable.ArrayBuffer[SceneCell])], nextCell:SceneCell) => {
        moveToCells.find(_._1.isAngleWithinMoveDegrees(fromCell.getDegreesTo(nextCell))).foreach(_._2.+=(nextCell))
          moveToCells
      })
  }

  //SceneView.printSceneCellMap(this.sceneCells)

  /**
    *
    * @return
    */
  override def toString: String = {
    this.cellString
  }
}


/**
  *
  */
object SceneView {

  /**
    *
    * @param botView
    * @return
    */
  def createInstance(botView: String): SceneView = {

    val dimens: Int = math.sqrt(botView.length).toInt

    val sceneCells: List[SceneCell] = (0 until botView.length).map(index => SceneCell(index % dimens, math.max(1, index) / dimens, getMinion(botView.charAt(index)))).toList

    new SceneView(sceneCells)
  }


  def printSceneCellMap(sceneCells:List[SceneCell]): Unit = {

    val columns:Int = sceneCells.last.x + 1
    val rows: Int = sceneCells.last.y + 1

    val result = sceneCells.indices.foldLeft(StringBuilder.newBuilder.append(
      (0 to columns by 1).foldLeft(StringBuilder.newBuilder.append(" ")
      )((builder: StringBuilder, index: Int) => builder.append(index % 10 + " ")).toString()))(
      (builder: StringBuilder, index: Int) =>
      {
        val cellChar: Char = sceneCells(index).minion.minionChar
        if (index % columns == 0) {
          val nIndex: String = String.valueOf((index / columns) % 10)
          val completeNIndex: String = nIndex + " "
          builder.append("\n" + completeNIndex)
        }
        builder.append(cellChar).append(" ")
      }).append("\n")

    result.append("Columns = " + columns + "\n")
    result.append("Rows = " + rows + "\n")
    result.append("Total cells = " + sceneCells.size + "\n")
    println(result)
  }

}
