package blackjack

import scala.collection.mutable.ArrayBuffer
import scala.io.Source
import java.io._

class Deck {
    var basePath = "C:\\Users\\Scott.Hutchison\\Documents\\Classes\\CS403\\cs403-blackjack\\blackjack\\src\\main\\scala\\blackjack\\"
    basePath = new File("").getAbsolutePath() + "\\src\\main\\scala\\blackjack\\"
    
    var deck : ArrayBuffer[Card]= new ArrayBuffer[Card]()
    
    def initializeDeck(loadFromFile : Boolean = false) = {
      deck = new ArrayBuffer[Card]()
      if (loadFromFile){
        val deckFile = "deck_sorted.txt"
        println("***INFO: loading deck from file: " + deckFile + "***")
        
        
        for (line <- Source.fromFile(basePath + deckFile).getLines.toList) {

          val stringLine = line.toString()
          val valueAndSuit = stringLine.split(",").toList
          val value = valueAndSuit(0).trim()
          val suit = valueAndSuit(1).trim()
          val card = new Card(value, suit)

          deck += card
        }
      }
      else {
        println("***INFO: Generating randomly shuffled deck***")        
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