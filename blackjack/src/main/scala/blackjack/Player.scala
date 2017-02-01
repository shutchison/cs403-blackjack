package blackjack

import scala.collection.mutable.ArrayBuffer

class Player(val name : String, val startingMoney : Int) {
  var money = startingMoney
  var hands : ArrayBuffer[Hand] = new ArrayBuffer[Hand]()
  var bet = decideBet()
  
  var activeHandIndex = 0
  
  def printPlayer() : ArrayBuffer[String] = {    
    var playerArray : ArrayBuffer[String] = new ArrayBuffer[String]()
    
    if (hands.length == 1) {
      playerArray += "Bet: $" + bet.toString() + " "*(14-bet.toString().length())
      playerArray += " "*20
         
      for (hand <- hands) {
        playerArray ++= hand.printHand()
      }
      playerArray += name + " "*(20-name.length())
      playerArray += "Money: $" + money.toString() + " "*(12-money.toString().length())         
    }
    else if (hands.length > 1) {
      var betString = ""
      for (hand <- hands) {
        betString += "Bet: $" + bet.toString() + " "*(14-bet.toString().length())
      }

      playerArray += betString
      playerArray += " "*(20*hands.length)
      
      var handsString = new ArrayBuffer[String]()
      for (hand <- hands) {
        var currentHand = hand.printHand()
        if (handsString.length == 0) {
          handsString ++= currentHand
        }
        else {
          for (i <- 0 until handsString.length) {
            handsString(i) += currentHand(i)
          }
        }
      }

      playerArray ++= handsString
      
      var nameString = ""
      for (i <- 1 until hands.length+1) {
        nameString += name + " hand " + i.toString() + " "*(14-name.length()-i.toString().length)      
      }
      playerArray += nameString
      
      var moneyString = ""
      for (i <- 1 until hands.length+1) {
        moneyString += "Money: $" + money.toString() + " "*(12-money.toString().length())      
      }
      playerArray += moneyString
    }
    
    
    //println(playerArray)
    return playerArray
  }
  
  def getActiveHand() : Hand = {
    return hands(activeHandIndex)
  }
  
  def decideBet() : Int = {
    // TODO: figure out a way to adjust bet as part of strategy

    return 5
  }
  
  def solicitDecision() : String = {
    var decision = ""
    // TODO: insert logic and return a string to tell the dealer what to do
    // Probably just a string that says "hit", "stand", "double", "split"
    // check for these strings in the Dealer class
    
    return decision
  }
}