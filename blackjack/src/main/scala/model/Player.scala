package model

import scala.collection.mutable.ArrayBuffer

class Player(val name : String, val startingMoney : Int) {
  var money = startingMoney
  var hands : ArrayBuffer[Hand] = new ArrayBuffer[Hand]()
  var bet = decideBet()
  
  var currentStrategy = "Dealer"
  
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

      playerArray += "Strategy: " + currentStrategy + " "*(10-currentStrategy.length())      
      
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
      
      var strategyString = ""

      for (i <- 1 until hands.length+1) {
        strategyString += "Strategy: " + currentStrategy + " "*(10-currentStrategy.length())      
      }
      playerArray += strategyString
    }
    
    
    //println(playerArray)
    return playerArray
  }
  
  def getActiveHand() : Hand = {
    return hands(activeHandIndex)
  }
  
  def decideBet() : Int = {
    // TODO: figure out a way to adjust bet as part of strategy
    bet = 5
    
    return bet
  }
  
  def solicitDecision(doPrints : Boolean = false) : String = {
    var decision = ""
    
    if (getActiveHand().getHandValue() < 17) {
      if (doPrints) {
        println(name + " says 'hit me!'")        
      }
      return "hit"
    }
    else if (getActiveHand().getHandValue() >21){
      if (doPrints) {
        println(name + " says 'bust'")
      }
      return "bust"
    }
    else {
      if (doPrints) {
        println(name + " says 'stand.'")
      }
      return "stand"
    }
    
  }
  
  def isLastHandToPlay() : Boolean = {
    if (hands.length == 1) {
      return true
    }
    else if (activeHandIndex == hands.length) {
      return true
    }
    else {
      return false
    }
  }
  
}