package blackjack

import scala.collection.mutable.ArrayBuffer

class Hand {
  var cards : ArrayBuffer[Card] = new ArrayBuffer[Card]()
  
  
  def getHandValue(): Int = {
    var handValue = 0
    for (card <- cards) {
      handValue += card.getCardValue()
    }
    return handValue
  }
  
  def printHand() : ArrayBuffer[String] = {
    val handArray :  ArrayBuffer[String] = new ArrayBuffer[String]()

    var cardString = ""
    for (card <- cards){
      cardString += card.value + card.suit + " "
    }
    handArray += cardString + " "*(20-cardString.length())
    
    val handValue = getHandValue()
    var totalString = "Hand total: " + handValue.toString()
    handArray += totalString + " "*(20-totalString.length())
    if (handValue > 21) {
      handArray += "*** BUSTED ***      "  
    }
    else {
      handArray += " "*20    
    }
    
    
    /*for (line <- handArray){
      println(line)
    }
    */
    return handArray 
  }
  
}