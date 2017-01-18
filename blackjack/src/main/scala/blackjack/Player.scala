package blackjack

import scala.collection.mutable.ArrayBuffer

class Player(val name : String) {
  var money = 100
  var hands : ArrayBuffer[Hand] = new ArrayBuffer[Hand]()
  var bet = 5
  
  var activeHandIndex = 0
  
  def printPlayer() : ArrayBuffer[String] = {
    var playerArray : ArrayBuffer[String] = new ArrayBuffer[String]()
    
    playerArray += "Bet: $" + bet.toString() + " "*(14-bet.toString().length())
    playerArray += " "*20
       
    for (hand <- hands) {
      playerArray ++= hand.printHand()
    }
    playerArray += name + " "*(20-name.length())
    playerArray += "Money: $" + money.toString() + " "*(12-money.toString().length())   
    
    //println(playerArray)
    return playerArray
  }
  
  def getActiveHand() : Hand = {
    return hands(activeHandIndex)
  }
  
  def solicitDecision() : String = {
    var decision = ""
    // TODO: insert logic and return a string to tell the dealer what to do
    // Probably just a string that says "hit", "stand", "double", "split"
    // check for these strings in the Dealer class
    
    return decision
  }
}