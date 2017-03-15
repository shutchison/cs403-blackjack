package view

import model._
import controller._

import scala.collection.mutable.ArrayBuffer

class textView {
    def showGameArea() : ArrayBuffer[String] = {
    var gameArea = new ArrayBuffer[String]()
    
    gameArea += "="*80
    gameArea += " "*35 + "BLACKJACK!"
    gameArea += "="*80
    gameArea += " "*80
    var dealerLines = Dealer2.printDealer()
    for (i <- 0 until dealerLines.length) {
      dealerLines(i) = " "*35 + dealerLines(i)
    }
    gameArea ++= dealerLines
    gameArea += " "*80
    gameArea += "-"*80
    var playerStrings = new ArrayBuffer[ArrayBuffer[String]]()
    
    for (player <- ActionQueue.getPlayersInAlphabeticalOrder) {
      playerStrings += player.printPlayer()
    }
    
    for (i <- 0 until playerStrings(0).length) {
      var line = ""
      for (player <- playerStrings.reverse) {
         line += player(i)
      }
      gameArea += line
    }
    gameArea += "Next Action: " + ActionQueue.front
    

    return gameArea
  }
}