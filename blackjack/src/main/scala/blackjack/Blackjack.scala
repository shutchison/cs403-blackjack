package blackjack

import view._
import model._
import controller._


import scala.util.control.Breaks._

object Blackjack {
  
  def main (args: Array[String]):Unit= {

    
    Dealer.initializeGame(true)
    
    for (i <- 0 until 20) {
      Dealer.doTurn(true, true)
    }
    
    val t = new textView()
    Dealer.doMove(true, true)
    for (line <- t.showGameArea()) println(line)
    println(ActionQueue.showPlayerOrder)
    Dealer.doTurn(true, true)
    //Dealer.doTurn(true, true)
    //val g = new GUI_view()
    
    println("done!")
  }
}