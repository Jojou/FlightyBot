package com.partridgetech.navigation

import com.partridgetech.common.{FlightyMove, SceneMinion}


/**
  * Each cell in the view is represented by SceneCell object.  Contains basic data and useful methods for interacting
  * with other classes such as FlightMove or other SceneCells.
  *
  * @param x location
  * @param y location
  * @param minion type
  */
case class SceneCell(x: Int, y: Int, minion: SceneMinion) extends Ordered[SceneCell] {

  /**
    * Get moves to cell.
    *
    * @param that other cell
    * @return distance
    */
  def getMoves(that: SceneCell): Int = {
    math.max(math.abs(this.x - that.x), math.abs(this.y - that.y))
  }

  /**
    * Get tuple containing x & y movements to reach cell.
    *
    * @param that other cell
    * @return tuple
    */
  private def getXandYtowardsCell(that: SceneCell): (Int, Int) = {
    val x = {
      val xDistance = this.x - that.x
      if (xDistance > 0) -1 else if (xDistance < 0) 1 else 0
    }
    val y = {
      val yDistance = this.y - that.y
      if (yDistance > 0) -1 else if (yDistance < 0) 1 else 0
    }
    (x, y)
  }


  /**
    * Get FlightyMove to target.
    *
    * @param to other cell
    * @return FlightyMove
    */
  def getMoveTo(to: SceneCell): FlightyMove = {
    val xyTuple = this.getXandYtowardsCell(to)
    FlightyMove.getMove(xyTuple._1, xyTuple._2)
  }

  /**
    * Get FlightyMove from target.
    *
    * @param from other cell
    * @return FlightyMove
    */
  def getMoveFrom(from: SceneCell): FlightyMove = {
    val xyTuple = from.getXandYtowardsCell(this)
    FlightyMove.getMove(xyTuple._1, xyTuple._2)
  }

  /**
    * Naively calculates which of two cells is closer.
    *
    * @param other other cell
    * @param target other cell
    * @return boolean indicating which is closer
    */
  def isCellCloserToThan(target: SceneCell, other: SceneCell): Boolean = {
    var result = this.getMoves(target) - other.getMoves(target)
    if (result == 0) result = this.compare(other)
    result < 0
  }

  /**
    * Distance from one cell to another using pi.
    *
    * @param that cell to measure distance to
    * @return measurement
    */
  def getPythagorasDistance(that: SceneCell): Int = {
    def sqr(v: Double): Double = v * v
    scala.math.sqrt(sqr(this.x - that.x) + sqr(this.y - that.y)).ceil.toInt
  }

  /**
    * Get degrees to other cell.
    *
    * @param that
    * @return
    */
  def getDegreesTo(that:SceneCell): Double = {
    val angle = math.toDegrees(math.atan2(that.y - this.y, that.x - this.x) + math.Pi / 2)
    if(angle < 0) angle + 360 else angle
  }

  override def compare(that: SceneCell): Int = {
    var result = this.y - that.y
    if (result == 0) result = this.x - that.x
    result
  }

  override def toString: String = "(X " + this.x + ",Y " + this.y + ", " + this.minion + ")"

  override def equals(other: Any): Boolean = {
    var result: Boolean = false
    if (other.isInstanceOf[SceneCell]) {
      val otherSceneCell = other.asInstanceOf[SceneCell]
      result = this.x == otherSceneCell.x && this.y == otherSceneCell.y && this.minion == otherSceneCell.minion
    }
    result
  }

  override def hashCode(): Int = this.x.hashCode() + this.y.hashCode()
}

