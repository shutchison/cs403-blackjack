package blackjack

import scala.collection.mutable.ArrayBuffer

object Blackjack {
  
  def main (args: Array[String]):Unit= {
    Dealer.dealNewHands(true)
    
    Dealer.playerQueue.advanceOrder()
    Dealer.playerQueue.advanceOrder()
    
    var h = new Hand()
    h.cards += Dealer.deck.deal() 
    h.cards += Dealer.deck.deal() 

    Dealer.playerQueue.getCurrentPlayer().hands += h
    
    Dealer.playerQueue.advanceOrder()
    Dealer.hit()
    
    var gameArea = showGameArea()
    
    for (line <- gameArea) {
      println(line)
    }
    
    println("done")
  }
  
  def initializeGame() = {
    Dealer.dealNewHands(true)
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

    //println(playerStrings)
    
    //println(gameArea)
    return gameArea
  }
}