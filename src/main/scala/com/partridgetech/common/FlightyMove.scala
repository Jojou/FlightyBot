package com.partridgetech.common


/**
  * Represents move of the entity.
  *
  * @param x -1 to 1 left, right, stationary
  * @param y -1 to 1 up, down, stationary
  * @param degrees tuple containing min and max degrees this move is closest to.  varargs only because center up
  *                contains two values (e.g.x 315D -> 360D, 0D -> 45D))
  */
case class FlightyMove(x: Int, y: Int, degrees:(Double, Double)*) {

  /**
    * Returns boolean indicating whether the input is within the degree range of move.
    *
    * @param testDegrees double to test
    * @return boolean
    */
  def isAngleWithinMoveDegrees(testDegrees:Double):Boolean = this.degrees.exists(degRange => testDegrees >= degRange._1 && testDegrees <= degRange._2)


  /**
    * @see java.lang.Enum#toString()
    */
  @Override
  override def toString: String = {
    String.valueOf(this.x) + ':' + this.y
  }
}

object FlightyMove {

  // Constant value
  val DegreesCircle:Double = 360

  // Constant value
  val EighthDegreesCircle:Double = DegreesCircle / 8

  // Constant value
  val SixteenthDegreesCircle:Double = DegreesCircle /16

  object CENTER_UP extends FlightyMove(0, -1, 315D -> 360D, 0D -> 45D)

  object RIGHT_UP extends FlightyMove(1, -1,  0D -> 90D)

  object RIGHT_CENTER extends FlightyMove(1, 0,  45D -> 135D)

  object RIGHT_DOWN extends FlightyMove(1, 1,   90D -> 180D)

  object CENTER_CENTER extends FlightyMove(0, 0)

  object CENTER_DOWN extends FlightyMove(0, 1, 135D -> 225D)

  object LEFT_DOWN extends FlightyMove(-1, 1, 180D -> 270D)

  object LEFT_CENTER extends FlightyMove(-1, 0, 225D -> 315D)

  object LEFT_UP extends FlightyMove(-1, -1,   270D -> 360D)

  // All existing moves.
  val AllMoves: Seq[FlightyMove] = List(LEFT_UP, CENTER_UP, RIGHT_UP, LEFT_CENTER, CENTER_CENTER, RIGHT_CENTER,
    LEFT_DOWN, CENTER_DOWN, RIGHT_DOWN)

  //All moves except CENTER_CENTER.
  val ActiveMoves: Seq[FlightyMove] = List(LEFT_UP, CENTER_UP, RIGHT_UP, LEFT_CENTER, RIGHT_CENTER,
    LEFT_DOWN, CENTER_DOWN, RIGHT_DOWN)

  // Moves on a diagonal.
  val DiagonalMoves: Seq[FlightyMove] = List(LEFT_UP, RIGHT_UP, RIGHT_DOWN, LEFT_DOWN)

  // Moves not on a diagonal.
  val NonDiagonalMoves: Seq[FlightyMove] = List(CENTER_UP, LEFT_CENTER, RIGHT_CENTER, CENTER_DOWN)

  /**
    * Get adjacent moves (preceding and succeeding) the requested move.
    *
    * @param flightyMove to get move before and after
    * @return seq containing two moves
    */
  def getAdjacentMoves(flightyMove: FlightyMove): Seq[FlightyMove] = {

    // If requested move is CENTER_CENTER then just change it to UP_CENTER as there are no adjacent moves.
    val activeMove = {
      if (flightyMove == FlightyMove.CENTER_CENTER) CENTER_UP else flightyMove
    }

    val targetIndex = ActiveMoves.indexOf(activeMove)
    Seq(ActiveMoves((targetIndex - 1 + ActiveMoves.length) % ActiveMoves.length),
      flightyMove,
      ActiveMoves((targetIndex + 1 + ActiveMoves.length) % ActiveMoves.length))
  }

  /**
    * Get move using x & y.  Note will throw errors if x or y is out of range.
    *
    * @param x coordinate
    * @param y coordinate
    * @return FlightyMove
    */
  def getMove(x: Int, y: Int): FlightyMove = AllMoves.find(move => move.x == x && move.y == y).get

  /**
    * Get move in opposite direction.
    *
    * @param flightyMove to get opposite of
    * @return opposite move
    */
  def getOppositeDirection(flightyMove: FlightyMove): FlightyMove = FlightyMove.getMove(
    {if (flightyMove.x < 0) math.abs(flightyMove.x) else -flightyMove.x}, {if (flightyMove.y < 0) math.abs(flightyMove.y) else -flightyMove.y})

}