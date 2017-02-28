package blackjack

import scala.collection.mutable.ArrayBuffer
import scala.util.control.Breaks._

object Blackjack {
  
  def main (args: Array[String]):Unit= {
    initializeGame(true)
    
    var gameLines = showGameArea()
    for (line <- gameLines) {
      println(line)    
    }
    
    Dealer.doGame(true, true)
    
    gameLines = showGameArea()
    for (line <- gameLines) {
      println(line)    
    }
  }
  
  def initializeGame(loadFromFile: Boolean = false) = {
    Dealer.playerQueue = new PlayerQueue()
    Dealer.dealNewHands(loadFromFile)
  }  


  def showGameArea() : ArrayBuffer[String] = {
    var gameArea = new ArrayBuffer[String]()
    
    gameArea += "="*80
    gameArea += " "*35 + "BLACKJACK!"
    gameArea += "="*80
    gameArea += " "*80
    var dealerLines = Dealer.printDealer()
    for (i <- 0 until dealerLines.length) {
      dealerLines(i) = " "*35 + dealerLines(i)
    }
    gameArea ++= dealerLines
    gameArea += " "*80
    gameArea += "-"*80
    var playerStrings = new ArrayBuffer[ArrayBuffer[String]]()
    
    for (player <- Dealer.playerQueue.players) {
      playerStrings += player.printPlayer()
    }
    
    for (i <- 0 until playerStrings(0).length) {
      var line = ""
      for (player <- playerStrings.reverse) {
         line += player(i)
      }
      gameArea += line
    }
    if (Dealer.dealerNeedsToTakeTurnNext) {
      gameArea += "Current Player: Dealer"
    }
    else {
      gameArea += "Current Player: " + Dealer.playerQueue.getCurrentPlayer().name    
    }
    
    
    //println(playerStrings)
    
    //println(gameArea)
    return gameArea
  }
//    
//def dealNewHands(loadFromFile: Boolean = false) = {
//    // Remove all players who can't afford the minimum bet of $5
//    breakable {
//      for (player <- playerQueue.players) {
//        if (player.money < 5 && player.name != "Dealer") {
//          playerQueue.removePlayer(player)
//          break
//        }
//      }
//    }
//
//    // bets are placed and all hands are emptied
//    for (player <- playerQueue.players) {
//        player.money -= player.decideBet()
//        while (player.hands.length > 0){
//          player.hands.remove(0)
//        }
//    }
//    
//    
//    Dealer.dealerHand = new Hand()
//    var newHands : ArrayBuffer[Hand] = new ArrayBuffer[Hand]()
//    
//    for (player <- playerQueue.players) {
//      newHands += new Hand()
//    } 
//
//    Dealer.deck.initializeDeck(loadFromFile)
//    for (i <- 0 until 2){
//      for (hand <- newHands) {
//        hand.cards += Dealer.deck.deal()
//      }
//    }
//    
//    for (player <- playerQueue.players) {
//      player.hands += newHands.remove(0)
//    }
//  }  
}