package model

import scala.collection.mutable.ArrayBuffer
import scala.util.control.Breaks._

class PlayerQueue() {
  //TODO: Need to get the deal in the player queue.  This needs to be worked on next...
  // maybe arraybuffer of objects?  Not sure about this...
  
  val startingMoney = 100
  
  val playerOneName   = "Alice"
  val playerTwoName   = "Bob"
  val playerThreeName = "Carl"
  val playerFourName  = "Doug"
  
  var players = new ArrayBuffer[Player]()
  players += new Player(playerOneName, startingMoney)
  players += new Player(playerTwoName, startingMoney)
  players += new Player(playerThreeName, startingMoney)
  players += new Player(playerFourName, startingMoney)
  
  var currentPlayer = 0
  
  var dealerNeedsToTakeNextTurn = false
  
  def showPlayerOrder(): String = {
    var playerString = ""
    var currentPlayerCounter = currentPlayer
    for (i <- 0 until (players.length)){
      val p = players(currentPlayerCounter)
      playerString += p.name

      currentPlayerCounter = (currentPlayerCounter+1) % players.length
      
      if (currentPlayerCounter != currentPlayer) {
        playerString += ", "
      }
    }
    
    //println(playerString)
    return playerString
  }
  
 
  def removePlayer(playerToRemove: Player) = {
    var index = 0
    breakable {
      for (player <- players) {
        if (player.name.equals(playerToRemove.name)) {
          println(players(index).name + " has run out of money and left the table!")
          players.remove(index)
          break
        }
        index += 1
      }      
    }
  }
  
  def getLastPlayer() : Player = {
    return players(players.length-1)
  }
  
  def restartQueue() = {
    currentPlayer = 0
  }
  
  def getCurrentPlayer() : Player = {
    return players(currentPlayer)    
  }
  
  def advanceOrder() = {
    currentPlayer = (currentPlayer+1) % players.length
  }  
}