package blackjack

import view._
import model._
import controller._


import scala.util.control.Breaks._

object Blackjack {
  
  def main (args: Array[String]):Unit= {
    val t = new textView()
    
    Dealer2.initializeGame(true)
    Dealer2.doTurn(true, true)
    for (line <- t.showGameArea()) println(line)
    //val g = new GUI_view()

    println("done!")
  }
}