package blackjack

import scala.collection.mutable.ArrayBuffer

class Player(val name : String) {
  var money = 100
  var hand : ArrayBuffer[Card] = new ArrayBuffer[Card]()
  
  def getHandValue(): Int = {
    var handValue = 0
    for (card <- hand) {
      handValue += card.points(card.value)
    }
    return handValue
  }
  
}