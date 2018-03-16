package com.partridgetech.command.spawn.choices

import com.partridgetech.attribute.OutputCommand
import com.partridgetech.common.{FlightyChoice, FlightyIntention, FlightyViewState}
import com.partridgetech.navigation.SceneCell

abstract class BaseSpawn(flightyViewState: FlightyViewState) extends FlightyChoice[OutputCommand](flightyViewState) {

  def getOrbitPosition: Int = -1

  def getIntention: FlightyIntention

  def getTargetSpawnCell: SceneCell

  def getSpawnEnergy:Int

  def getTargetCooldown: (SceneCell, Int)

  def getName: String = this.getIntention.toString + this.requestRecord.time

  def getOutcome: OutputCommand = {

    val targetCooldown = this.getTargetCooldown

    OutputCommand.Spawn(
      this.flightyViewState.getMainCellMoveTo(this.getTargetSpawnCell),
      this.getName,
      this.getSpawnEnergy,
      this.getIntention,
      this.requestRecord.timeElapsed,
      this.getOrbitPosition,
      targetCooldown._1,
      targetCooldown._2,
      this.requestRecord.timeElapsed)
  }

  override def enact: Boolean =
    (this.flightyViewState.adjacentEmptyCells.nonEmpty
      && this.requestRecord.energy >= BaseSpawn.MinimumEnergy
      && !(this.flightyViewState.closestEnemySlave.isDefined && this.flightyViewState.closestEnemySlave.get
      .getMoves(this.flightyViewState.mainCell) < BaseSpawn.EnemyMinDistance)
      && !(this.flightyViewState.closestFriendlySlave.isDefined && this.flightyViewState.closestFriendlySlave.get
      .getMoves(this.flightyViewState.mainCell) < BaseSpawn.MaxCloseFriendlySlaves)
      )
}

object BaseSpawn {
  val MinimumEnergy: Int = 100
  val MinimumEnemySlaveDistance: Int = 3
  val EnemyMinDistance = 4
  val MaxCloseFriendlySlaves = 3
  val MaxSpawnEnergy = 1000
}
