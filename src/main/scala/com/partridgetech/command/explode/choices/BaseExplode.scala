package com.partridgetech.command.explode.choices

import com.partridgetech.attribute.OutputCommand
import com.partridgetech.common.{FlightyChoice, FlightyViewState}
import com.partridgetech.navigation.SceneCell

import scala.collection.mutable.ArrayBuffer

abstract class BaseExplode(flightyViewState: FlightyViewState) extends FlightyChoice[OutputCommand](flightyViewState) {

  val entityDamage: Int = math.min(1000, this.requestRecord.energy)

  /** Blast radius. */
  val enemyCellsToDistance: Seq[(SceneCell, Int)] = this.flightyViewState.enemyBots.foldLeft(ArrayBuffer[(SceneCell, Int)]())(
    (cellsToDistance: ArrayBuffer[(SceneCell, Int)], nextCell: SceneCell) => cellsToDistance.+=:(
      nextCell, this.flightyViewState.mainCell.getPythagorasDistance(nextCell)))

  val radiusToDamage: List[(Int, Double)] = BaseExplode.ExplodeRange.map(
    distance => distance -> this.enemyCellsToDistance.filter(cellToDist => cellToDist._2 < distance)
      .foldLeft(0.0)((totalDamage: Double, nextCellDistance: (SceneCell, Int)) =>
        totalDamage + this.damageForDistance(distance, this.entityDamage,
          nextCellDistance._2))).toList

  val bestRadiusToDamage:(Int, Double) = this.radiusToDamage.maxBy(_._2)

  def getBlastArea(blastRad: Int): Double = {
    blastRad.toDouble * blastRad.toDouble * scala.math.Pi
  }

  def energyPerArea(blastRad: Int, energy: Int): Double = {
    energy.toDouble / this.getBlastArea(blastRad)
  }

  def damageAtCenter(blastRad: Int, energy: Int): Double = {
    scala.math.abs(this.energyPerArea(blastRad, energy) * -200)
  }

  def damageForDistance(blastRad: Int, energy: Int, pythagorasDistance: Double): Double = {
    this.damageAtCenter(blastRad, energy) * (1.0 - (pythagorasDistance.toDouble / blastRad.toDouble))
  }
}

object BaseExplode {
  val ExplodeRange:Range = 2 to 8 by 1
}