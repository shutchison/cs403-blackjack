package blackjack

object Blackjack {
  
  def main (args: Array[String]):Unit= {
    var t = new Table()
    t.deck.initializeDeck(true)
    t.deck.printDeck()

    t.showPlayerOrder()
    println("done")
    t.newHand(true)
    
    t.showGameArea()
    
    t.hit()
    t.showGameArea()
    t.advanceOrder()
    t.showGameArea()
    t.hit()
    t.showGameArea()
  }
  

}