package blackjack

import scala.collection.mutable.ArrayBuffer

class PlayerQueue(startingMoney: Int) {
  
  val players = Array[Player](new Player("Alice", startingMoney), 
                              new Player("Bob", startingMoney),
                              new Player("Carl", startingMoney),
                              new Player("Doug", startingMoney))
  
  var currentPlayer = 0
  
  def showPlayerOrder(): String = {
    var playerString = ""
    var currentPlayerCounter = currentPlayer
    for (i <- 0 until (players.length)){
      playerString += players(currentPlayerCounter).name
      
      currentPlayerCounter = (currentPlayerCounter+1) % players.length
      
      if (currentPlayerCounter != currentPlayer) {
        playerString += ", "
      }
    }
    
    //println(playerString)
    return playerString
  }
  
  def getCurrentPlayer() : Player = {
    return players(currentPlayer)
  }
  
  def advanceOrder() = {
    currentPlayer = (currentPlayer+1) % players.length
  }
  
}