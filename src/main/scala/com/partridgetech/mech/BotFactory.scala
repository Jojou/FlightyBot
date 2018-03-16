package com.partridgetech.mech

import com.partridgetech.command.essence._
import com.partridgetech.command.explode.{EnemyMasterDetonation, OpportunisticDetonation}
import com.partridgetech.command.move.{SlaveAttackMove, _}
import com.partridgetech.command.spawn.choices.SlaveScoutSpawn
import com.partridgetech.command.spawn.{MasterSpawn, WorkerSpawn}
import com.partridgetech.command.status.StatusReport
import com.partridgetech.common.{FlightyIntention, FlightyViewState}
import com.partridgetech.model.ReactRequestRecord

/**
  *
  * @param intention
  * @param botResponsePlaceHolder
  */
case class BotFactory(intention: FlightyIntention, botResponsePlaceHolder: (FlightyIntention, FlightyViewState) => FlightyBot) {

  def createBot(flightyViewState: FlightyViewState): FlightyBot = this.botResponsePlaceHolder(
    intention, flightyViewState)
}

object BotFactory {

  val BotReturnEnergy = 1000

  val BotReturnTime = 150

  val MasterFloatEnergyRequirement = 1000

  object IntrepidMaster extends BotFactory(FlightyIntention.RapidExpansion, new FlightyBot(_, _) with StatusReport with BotEssence with MasterSpawn with HastyMasterMove)

  object CautiousMaster extends BotFactory(FlightyIntention.SafelyFloat, new FlightyBot(_, _) with StatusReport with BotEssence with MasterSpawn with CautiousMasterMove)

  object ScoutSlave extends BotFactory(FlightyIntention.Scout, new FlightyBot(_, _) with StatusReport with BotEssence with OpportunisticDetonation with WorkerSpawn with SlaveExploreMove)

  object SummonedSlave extends BotFactory(FlightyIntention.HeadHome, new FlightyBot(_, _) with StatusReport with BotEssence with OpportunisticDetonation with SlaveReturnMove)

  object AttackSlave extends BotFactory(FlightyIntention.Attack, new FlightyBot(_, _) with StatusReport with BotEssence with EnemyMasterDetonation with SlaveAttackMove )

  val bots: List[BotFactory] = List(IntrepidMaster, CautiousMaster, ScoutSlave, SummonedSlave, AttackSlave)

  def createInstance(reactRequestRecord: ReactRequestRecord): FlightyBot = {
    val flightyViewState: FlightyViewState = new FlightyViewState(reactRequestRecord)
    if (reactRequestRecord.intention.getIsMaster) BotFactory.createMaster(flightyViewState)
    else BotFactory.createSlave(flightyViewState)
  }

  private def createMaster(flightyViewState: FlightyViewState): FlightyBot = {
    // After the first 5% of game time is up if master bot has over MasterFloatEnergyRequirement it will play more
    // conservatively.
    if (flightyViewState.requestRecord.energy > MasterFloatEnergyRequirement && flightyViewState.requestRecord.getBotAge > flightyViewState.requestRecord.welcomeRequestRecord.timeLimit / 20)
      BotFactory.CautiousMaster.createBot(flightyViewState)
    else
      BotFactory.IntrepidMaster.createBot(flightyViewState)

  }

  private def createSlave(flightyViewState: FlightyViewState): FlightyBot = {
      // If enemy master bot is visible and their is sufficient time left attack enemy master bot.
    if (flightyViewState.enemyMaster.isDefined && flightyViewState.requestRecord.getTimeRemaining > BotReturnTime){
      BotFactory.AttackSlave.createBot(flightyViewState)
    }
      // If energy is greater than 10% of total time and bot's age is greater than 10% of total time or time is running
      // low then return to master
    else if((flightyViewState.requestRecord.energy > flightyViewState.requestRecord.welcomeRequestRecord.timeLimit / 10 &&
      flightyViewState.requestRecord.getBotAge >  flightyViewState.requestRecord.welcomeRequestRecord.timeLimit/ 10)  ||
      flightyViewState.requestRecord.getTimeRemaining < BotReturnTime) BotFactory.SummonedSlave.createBot(flightyViewState)
      // If bot fails to find enemy bot because it has vacated far enough from target cell then become a scout cell
    else if (flightyViewState.requestRecord.intention == FlightyIntention.Attack && flightyViewState.requestRecord.targetCooldown == 0) {
      BotFactory.ScoutSlave.createBot(flightyViewState)
    }
    else this.getMatchingBot(flightyViewState).createBot(flightyViewState)
  }

  def getMatchingBot(flightyViewState: FlightyViewState):BotFactory = {
    this.bots.find(bot => bot.intention == flightyViewState.requestRecord.intention).get
  }
}