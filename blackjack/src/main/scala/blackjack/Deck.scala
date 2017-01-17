package blackjack

import scala.collection.mutable.ArrayBuffer
import scala.io.Source

class Card(val value : String, val suit : String) {
  val points = Map("2"->2, "3"->3, "4"->4, "5"->5,
                   "6"->6, "7"->7, "8"->8,"9"->9,"10"->10,
                   "J"->10,"Q"->10,"K"->10,"A"->11)
  
  def getCardValue() : Int = {
    return points(suit)
  }
}

class Deck {
    var deck : ArrayBuffer[Card]= new ArrayBuffer[Card]()
    
    def initializeDeck(loadFromFile : Boolean = false) = {
      deck = new ArrayBuffer[Card]()
      if (loadFromFile){
        val deckFile = "C:\\Users\\Scott.Hutchison\\Documents\\Classes\\CS403\\cs403-blackjack\\blackjack\\src\\main\\scala\\blackjack\\deck.txt"
        
        for (line <- Source.fromFile(deckFile).getLines.toList) {

          val stringLine = line.toString()
          val valueAndSuit = stringLine.split(",").toList
          val value = valueAndSuit(0).trim()
          val suit = valueAndSuit(1).trim()
          val card = new Card(value, suit)

          deck += card
        }
      }
      else {
        
        for (i <- 2 to 10){
          deck += new Card(i.toString(), "C")
          deck += new Card(i.toString(), "D")
          deck += new Card(i.toString(), "H")
          deck += new Card(i.toString(), "S")
        }
        for (i <- "JQKA"){
          deck += new Card(i.toString(), "C")
          deck += new Card(i.toString(), "D")
          deck += new Card(i.toString(), "H")
          deck += new Card(i.toString(), "S")
        }
        
        deck = scala.util.Random.shuffle(deck)
      }      
    }
    
    def printDeck() = {
      for (card <- deck){
        println(f"${card.value}%2s, ${card.suit}")
      }
    }
    
    def deal() : Card = {
      return deck.remove(0)
    }
}