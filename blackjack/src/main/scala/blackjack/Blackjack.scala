package blackjack

import scala.collection.mutable.ArrayBuffer

object Blackjack {
  
  def main (args: Array[String]):Unit= {
    var dealer = new Dealer()
    dealer.dealNewHands(true)
    
    dealer.hit()
    dealer.playerQueue.advanceOrder()
    dealer.hit()
    dealer.hit()
    
    val game = showGameArea(dealer)
    for (line <- game) {
      println(line)
    }
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
    
    // TODO: make this work if players have more than one hand.
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