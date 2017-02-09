package blackjack

import scala.collection.mutable.ArrayBuffer

class Hand {
  var cards : ArrayBuffer[Card] = new ArrayBuffer[Card]()
  
  
  def getHandValue(): Int = {
    var handValue = 0
    var numAces = 0
    
    for (card <- cards) {
      handValue += card.getCardValue()
    }
        
    for (card <- cards) {
      if (card.value == "A"){
        numAces += 1
      }
    }
    
    if (numAces == 0) {
      return handValue
    }
    else {
      if (handValue <= 21) {
        return handValue  
      }
      else{
        while (handValue > 21) {
          if (numAces == 0) {
            return handValue
          }
          else if (numAces > 0) {
            handValue -= 10
            numAces -= 1
          }
        }
        return handValue
      }
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
    else if (handValue == 21 && cards.length == 2){
      handArray += "*** BLACKJACK! ***  "       
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