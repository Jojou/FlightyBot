package com.partridgetech.common

import com.partridgetech.model.ReactRequestRecord
import com.partridgetech.navigation.{SceneCell, SceneView}

class FlightyViewState(val requestRecord: ReactRequestRecord) {

  // The world as seen by the mainCell.
  val sceneView: SceneView = requestRecord.sceneView

  // The cell for which this choice is being made
  val mainCell: SceneCell = sceneView.currentCell

  val closestEnemySlave: Option[SceneCell] = {
    if (this.enemySlaves.isEmpty) Option.empty
    else
      Option.apply(this.sceneView.getClosestCellToTarget(this.mainCell,
        this.enemySlaves))
  }

  val closestSnorg: Option[SceneCell] = {
    if (this.snorgs.isEmpty) Option.empty
    else
      Option.apply(this.sceneView.getClosestCellToTarget(this.mainCell,
        this.snorgs))
  }

  val closestFriendlySlave: Option[SceneCell] = {
    if (this.friendlySlaves.isEmpty) Option.empty
    else
      Option.apply(this.sceneView.getClosestCellToTarget(this.mainCell,
        this.friendlySlaves.filterNot(_ == this.mainCell)))
  }

  val closestWall: Option[SceneCell] = {
    if (this.walls.isEmpty) Option.empty
    else
      Option.apply(this.sceneView.getClosestCellToTarget(this.mainCell,
        this.walls.filterNot(_ == this.mainCell)))
  }

  val lastVisitedCells: List[SceneCell] = {
    this.requestRecord.moveHistory.foldLeft(List[SceneCell](this.mainCell))((visitedCells: List[SceneCell],
                                                                             previousMove: FlightyMove) => visitedCells.:+(this.sceneView.getCell(visitedCells.last.x - previousMove.x,
      visitedCells.last.y - previousMove.y))).drop(1)
  }

  lazy val snorgs: Seq[SceneCell] = this.sceneView.getCellsWithMinion(SceneMinion.Snorg)

  lazy val enemyBots: Seq[SceneCell] = this.sceneView.getCellsWithMinion(SceneMinion.EnemyBots)

  lazy val emptyCells: Seq[SceneCell] = this.sceneView.getCellsWithMinion(SceneMinion.Empty)

  lazy val friendlySlaves: Seq[SceneCell] = this.sceneView.getCellsWithMinion(SceneMinion.FriendlySlave)
    .filterNot(_ == mainCell)

  lazy val enemySlaves: Seq[SceneCell] = this.sceneView.getCellsWithMinion(SceneMinion.EnemySlave)

  lazy val walls: Seq[SceneCell] = this.sceneView.getCellsWithMinion(SceneMinion.Wall)

  lazy val unknownCells: Seq[SceneCell] = this.sceneView.getCellsWithMinion(SceneMinion.Unknown)

  lazy val enemyMaster: Option[SceneCell] = this.sceneView.getCellsWithMinion(SceneMinion.EnemyMaster)
    .headOption

  lazy val consumableCells: Seq[SceneCell] = this.sceneView.getCellsWithMinion(SceneMinion.CONSUMABLE_MINIONS)

  lazy val allBotCells: Seq[SceneCell] = this.sceneView.getCellsWithMinion(SceneMinion.ALL_BOTS)

  val adjacentEmptyCells: Seq[SceneCell] = this.sceneView.getAdjacentCells(this.mainCell)
    .filter(cell => cell.minion.equals(SceneMinion.Empty) || cell.minion.equals(SceneMinion.Fluppet)
      || cell.minion.equals(SceneMinion.Zugar))

  val safeAdjacentCells: Seq[SceneCell] = this.adjacentEmptyCells.filter(cell => this.snorgs
    .forall(_.getMoves(cell) > FlightyViewState.MINIMUM_SNORG_DISTANCE))

  val masterLocation: SceneCell = {
    if (isMaster) this.mainCell
    else {
      this.sceneView.getCell(
        this.mainCell.x + this.requestRecord.relativeMasterLocation._1,
        this.mainCell.y + this.requestRecord.relativeMasterLocation._2)
    }
  }

  def getCellsAtDistance(targetCell:SceneCell, distance:Int):List[SceneCell] = {
    val cornerCells: List[SceneCell] = FlightyMove.DiagonalMoves.map(move => this.sceneView.getCell(targetCell, move, distance)).toList
    cornerCells.foldLeft(List(cornerCells.last))((cells: List[SceneCell], cornerCell: SceneCell
                                                 ) => cells.++(this.sceneView.getCellsBetween(cells.last, cornerCell))).distinct
  }

  lazy val orbitCells: List[SceneCell] = {
    val cornerCells: List[SceneCell] = FlightyMove.DiagonalMoves.map(move => this.sceneView.getCell(this
      .masterLocation, move, 2)).toList
    val orbitCells = this.getCellsAtDistance(this.masterLocation, 2).filterNot(cell => cornerCells.contains(cell))

    val orbitSpacing = orbitCells.size / 4

    val baseTime = this.requestRecord.timeElapsed / 8

    List(
      orbitCells(baseTime % orbitCells.size),
      orbitCells((baseTime + orbitSpacing) % orbitCells.size),
      orbitCells((baseTime + (orbitSpacing * 2)) % orbitCells.size),
      orbitCells((baseTime + (orbitSpacing * 3)) % orbitCells.size))
  }

  // If bot is at edge cell may not exist
  def getOrbitAtIndex(index: Int): Option[SceneCell] = {
    val targetIndex = ((this.requestRecord.time / 10) + (index * 3)) % 12
    if (this.orbitCells.size < targetIndex) Option.empty
    else Option(this.orbitCells(targetIndex))
  }

  // Convenience method
  def getMainCellDistanceTo(other: SceneCell): Int = this.mainCell.getMoves(other)

  def getMainCellMoveTo(other: SceneCell): FlightyMove = this.mainCell.getMoveTo(other)

  def getMainCellMoveFrom(other: SceneCell): FlightyMove = this.mainCell.getMoveFrom(other)

  def isMaster: Boolean = math.sqrt(this.sceneView.sceneCells.size) == FlightyViewState.MASTER_VIEW_ROWS
}


object FlightyViewState {

  val Orbit_Speed_Factor_Reduction = 3
  val MINIMUM_SNORG_DISTANCE = 2
  val MINIMUM_ENEMY_SLAVE_DISTANCE = 4
  val BLAST_AVOID_RADIUS = 5
  val MASTER_VIEW_ROWS = 31
}
