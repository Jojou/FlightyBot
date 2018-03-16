package com.partridgetech.model

import com.partridgetech.attribute.InputAttributeConverter
import com.partridgetech.common.{FlightyIntention, FlightyMove, SceneMinion}
import com.partridgetech.common.attribute.Attribute
import com.partridgetech.mech.BotFactory
import com.partridgetech.navigation.{SceneCell, SceneView}

/**
  * Contains data from server.
  *
  * @param welcomeRequestRecord Initial server response
  * @param properties of record
  */
class ReactRequestRecord(val welcomeRequestRecord: WelcomeRequestRecord, properties: Map[String, String]) extends RequestRecord {

  val generation:Int = InputAttributeConverter.getInputAttribute(InputAttributeConverter.IntegerConverter,
    properties.get(Attribute.GENERATION.getName))
  val name:String = InputAttributeConverter.getInputAttribute(InputAttributeConverter.StringConverter,
    properties.get(Attribute.NAME.getName))
  val time:Int = InputAttributeConverter.getInputAttribute(InputAttributeConverter.IntegerConverter,
    properties.get(Attribute.TIME.getName))
  val intention:FlightyIntention = InputAttributeConverter.getInputAttribute(InputAttributeConverter.IntentionConverter,
    properties.get(Attribute.INTENTION.getName))
  val energy:Int = InputAttributeConverter.getInputAttribute(InputAttributeConverter.IntegerConverter,
    properties.get(Attribute.ENERGY.getName))
  val relativeMasterLocation:(Int, Int) = InputAttributeConverter.getInputAttribute(InputAttributeConverter.SceneCellCoordinateConverter,
    properties.get(Attribute.MASTER.getName))
  val moveHistory: List[FlightyMove] = InputAttributeConverter.getInputAttribute(InputAttributeConverter.MoveHistoryConverter,
    properties.get(Attribute.MOVE_HISTORY.getName))
  val timeCreated:Int = InputAttributeConverter.getInputAttribute(InputAttributeConverter.IntegerConverter,
    properties.get(Attribute.TIME_CREATED.getName))
  val orbitPosition:Int = InputAttributeConverter.getInputAttribute(InputAttributeConverter.IntegerConverter,
    properties.get(Attribute.ORBITPOSITION.getName))
  val latestView: String = {
    val inputBuilder: StringBuilder = StringBuilder.newBuilder.append(InputAttributeConverter.getInputAttribute(InputAttributeConverter.StringConverter,
      properties.get(Attribute.VIEW.getName)))
    inputBuilder.update(inputBuilder.size / 2, SceneMinion.CurrentEntity.minionChar)
    inputBuilder.toString()
  }

  val timeElapsed:Int = InputAttributeConverter.getInputAttribute(InputAttributeConverter.IntegerConverter, properties.get(Attribute.TIME_ELAPSED.getName))

  val view: String = InputAttributeConverter.getInputAttribute(InputAttributeConverter.StringConverter,
    properties.get(Attribute.MAP_MEMORY.getName))

  val viewDimension: Int = InputAttributeConverter.getInputAttribute(InputAttributeConverter.IntegerConverter,
    properties.get(Attribute.VIEW_DIMENSION.getName))

  val sceneView: SceneView = SceneView.createInstance(latestView)

  val targetCell: SceneCell = {
    val coordinates: (Int, Int) = InputAttributeConverter.getInputAttribute(InputAttributeConverter.SceneCellCoordinateConverter,
      properties.get(Attribute.TARGET_CELL.getName))
    // Factor in bots last move to update position of targetCell
    this.sceneView.getCell(sceneView.getCell(coordinates._1, coordinates._2), FlightyMove
      .getOppositeDirection(this.moveHistory.head))
    sceneView.getCell(coordinates._1, coordinates._2)
  }

  val targetCooldown: Int = InputAttributeConverter.getInputAttribute(InputAttributeConverter.IntegerConverter,
    properties.get(Attribute.TARGET_COOLDOWN.getName))

  override def requestResponse: String = BotFactory.createInstance(this).getResponse.toString()

  def getTimeRemaining: Int = {
    this.welcomeRequestRecord.timeLimit - this.time
  }

  def getBotAge: Int = {
    this.timeElapsed - this.timeCreated
  }

}