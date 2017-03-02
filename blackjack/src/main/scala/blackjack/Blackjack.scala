package blackjack

import view._
import model._
import controller._


import scala.util.control.Breaks._

object Blackjack {
  
  def main (args: Array[String]):Unit= {
    Dealer.initializeGame(true)
    
    val textView = new textView()
    
    var gameLines = textView.showGameArea()
    for (line <- gameLines) {
      println(line)    
    }
    
    val g = new GUI_view()
    
  }
  




}