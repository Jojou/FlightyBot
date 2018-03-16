package com.partridgetech.common

case class SceneMinion(minionChar: Char)

object SceneMinion {

  object FriendlyMaster extends SceneMinion('M')

  object EnemyMaster extends SceneMinion('m')

  object Unknown extends SceneMinion('?')

  object Wall extends SceneMinion('W')

  object Empty extends SceneMinion('_')

  object FriendlySlave extends SceneMinion('S')

  object EnemySlave extends SceneMinion('s')

  object Zugar extends SceneMinion('P')

  object Toxifera extends SceneMinion('p')

  object Fluppet extends SceneMinion('B')

  object Snorg extends SceneMinion('b')

  object CurrentEntity extends SceneMinion('C')

  val minions: Set[SceneMinion] = Set[SceneMinion](FriendlyMaster, EnemyMaster, Unknown, Wall, Empty,
    FriendlySlave, EnemySlave, Zugar, Toxifera, Fluppet, Snorg, CurrentEntity)

  def getMinion(minionChar: Char): SceneMinion = this.minions.find(min => min.minionChar.equals(minionChar)).get

  def getBlockingMinions(isMaster: Boolean): Seq[SceneMinion] = {
    if (isMaster) Seq[SceneMinion](EnemyMaster, Snorg, Wall, Toxifera)
    else Seq[SceneMinion](FriendlySlave, Snorg, Wall, EnemyMaster, EnemySlave, Toxifera)
  }

  val enemyNeutrals: Seq[SceneMinion] = Seq[SceneMinion](SceneMinion.Snorg, SceneMinion.Toxifera)

  val CONSUMABLE_MINIONS: Seq[SceneMinion] = Seq[SceneMinion](SceneMinion.Fluppet, SceneMinion.Zugar)

  val EnemyBots: Seq[SceneMinion] = Seq[SceneMinion](SceneMinion.EnemySlave, SceneMinion.EnemyMaster)

  val neutralBeasts: Seq[SceneMinion] = Seq[SceneMinion](SceneMinion.Snorg, SceneMinion.Fluppet)

  val friendlyBots: Seq[SceneMinion] = Seq(SceneMinion.FriendlySlave, SceneMinion.FriendlyMaster)

  val ALL_BOTS: Seq[SceneMinion] = Seq(SceneMinion.FriendlySlave, SceneMinion.FriendlyMaster,
    SceneMinion.EnemyMaster, SceneMinion.EnemySlave, SceneMinion.CurrentEntity)

  val splashable: Seq[SceneMinion] = Seq(SceneMinion.EnemySlave, SceneMinion.EnemyMaster, SceneMinion.Snorg,
    SceneMinion.Fluppet)
}