package blackjack

import scala.collection.mutable.ArrayBuffer

object Blackjack {
  
  def main (args: Array[String]):Unit= {
    var dealer = new Dealer()
    dealer.dealNewHands(true)
    
    dealer.playerQueue.advanceOrder()
    dealer.playerQueue.advanceOrder()
    
    var h = new Hand()
    h.cards += dealer.deck.deal() 
    h.cards += dealer.deck.deal() 

    dealer.playerQueue.getCurrentPlayer().hands += h
    
    dealer.playerQueue.advanceOrder()
    dealer.hit()
    
    var gameArea = showGameArea(dealer)
    
    for (line <- gameArea) {
      println(line)
    }
    
    println("done")
  }
  
  def initializeGame() : Dealer = {
    var dealer = new Dealer()
    dealer.dealNewHands(true)
    
    return dealer
  }
  
  def showGameArea(dealer : Dealer) : ArrayBuffer[String] = {
    var gameArea = new ArrayBuffer[String]()
    
    gameArea += "="*80
    gameArea += " "*35 + "BLACKJACK!"
    gameArea += "="*80
    gameArea += " "*80
    var dealerLines = dealer.printDealer()
    for (i <- 0 until dealerLines.length) {
      dealerLines(i) = " "*35 + dealerLines(i)
    }
    gameArea ++= dealerLines
    gameArea += " "*80
    gameArea += "-"*80
    var playerStrings = new ArrayBuffer[ArrayBuffer[String]]()
    
    for (player <- dealer.playerQueue.players) {
      playerStrings += player.printPlayer()
    }
    
    for (i <- 0 until playerStrings(0).length) {
      var line = ""
      for (player <- playerStrings.reverse) {
         line += player(i)
      }
      gameArea += line
    }

    //println(playerStrings)
    
    //println(gameArea)
    return gameArea
  }
}