package model

import controller._
import view._

import scala.collection.mutable.Map
import scala.collection.mutable.ArrayBuffer

object ActionQueue extends scala.collection.mutable.Queue[String]{
  val playerOneName   = "Alice"
  val playerTwoName   = "Bob"
  val playerThreeName = "Carl"
  val playerFourName  = "Doug"
  
  val startingMoney = 100
  
  var playerMap = Map(playerOneName -> new Player(playerOneName, startingMoney),
      playerTwoName -> new Player(playerTwoName, startingMoney),
      playerThreeName -> new Player(playerThreeName, startingMoney),
      playerFourName -> new Player(playerFourName, startingMoney))

      
  val initialQueueInput = Array("DealNewHands", playerOneName, playerTwoName, playerThreeName, playerFourName, "Dealer", "Payout", "CheckForWinner")
    
  for (element <- initialQueueInput) {
    this.enqueue(element)  
  }
 
  def getPlayersInAlphabeticalOrder : ArrayBuffer[Player] = {
    var playerArray = new ArrayBuffer[Player]()
    
    for (player <- playerMap.values.toList.sortBy(_.name)) {
      playerArray += player  
    }
    
    return playerArray
  }
  
  def reset = {
    playerMap = Map(playerOneName -> new Player(playerOneName, startingMoney),
      playerTwoName -> new Player(playerTwoName, startingMoney),
      playerThreeName -> new Player(playerThreeName, startingMoney),
      playerFourName -> new Player(playerFourName, startingMoney))

    while (this.length > 0){
      this.dequeue()      
    }
    
    for (element <- initialQueueInput) {
      this.enqueue(element)
    }  
  }
  
  def removeBrokePlayers = {
    var playersToRemove = new ArrayBuffer[String]()
    // Remove from the map
    for (player <- playerMap.values) {
      if (player.money < 5) {
        playerMap -= player.name
        playersToRemove += player.name
      }
    }
    // Remove from the queue
    var currentQueue = new ArrayBuffer[String]()
    while (!this.isEmpty){
      currentQueue += this.dequeue()
    }
    while (!currentQueue.isEmpty) {
      var queueItem = currentQueue.remove(0)
      if (!playersToRemove.contains(queueItem)){
        this.enqueue(queueItem)
      }
    }   
  }
  
  def getCurrentPlayer = {
  }
  
  def showPlayerOrder : String = {
    this.toList.mkString(", ")
  }
  
  def advanceActionOrder = {
    //println("advancing player order")
    var currentAction = this.dequeue()
    this.enqueue(currentAction)
    currentAction
  }
  
  def peekCurrentAction: Either[Player, String] = {
    val currentAction = this.front
    
    if (playerMap.get(currentAction) != None) {
      Left((playerMap.get(currentAction).get))
    }
    else {
      Right(currentAction)
    }
  }
  
  def popCurrentAction : Either[Player, String] = {
    val currentAction = this.dequeue
    this.enqueue(currentAction)
    
    if (playerMap.get(currentAction) != None) {
      Left((playerMap.get(currentAction).get))
    }
    else {
      Right(currentAction)
    }
  }
  
  def getNumberActivePlayers : Int = {
    playerMap.keys.toList.length
  }
  
  def getPlayers : Iterable[Player] = {
    playerMap.values
  }
}