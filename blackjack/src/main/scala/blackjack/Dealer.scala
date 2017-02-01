package blackjack

import scala.collection.mutable.ArrayBuffer

object Dealer {
  val playerStartingMoney = 100
  
  var deck = new Deck()
  var playerQueue = new PlayerQueue(playerStartingMoney)
  var dealerHand = new Hand()

  def dealNewHands(loadFromFile: Boolean = false) = {
    // empty all hands hands first
    for (player <- playerQueue.players) {
      while (player.hands.length > 0){
        player.hands.remove(0)
      }
    }

    dealerHand = new Hand()
    var newHands : ArrayBuffer[Hand] = new ArrayBuffer[Hand]()
    
    for (player <- playerQueue.players) {
      newHands += new Hand()
    } 

    deck.initializeDeck(loadFromFile)
    for (i <- 0 until 2){
      for (hand <- newHands) {
        hand.cards += deck.deal()
      }
      dealerHand.cards += deck.deal()
    }
    for (player <- playerQueue.players) {
      player.hands += newHands.remove(0)
    }
  }    

  def shuffle(loadFromFile : Boolean = false) = {
    deck.initializeDeck(loadFromFile)
  }
  
  def printDealer() : ArrayBuffer[String] = {
    var dealerArray : ArrayBuffer[String] = new ArrayBuffer[String]()
    dealerArray ++= dealerHand.printHand()
    dealerArray += "Dealer" + " "*14
    
    return dealerArray
  }

  def hit() = {
    val currentPlayer = playerQueue.getCurrentPlayer()
    currentPlayer.getActiveHand().cards += deck.deal
  }
  
  def stand() = {
    playerQueue.advanceOrder()
  }
  
  def checkForWinner() : String = {
      var winner = "None"
      
      for (player <- playerQueue.players) {
        if (player.money >= playerStartingMoney*2) {
          if (winner == "None"){
            winner = player.name           
          }
          else {
            winner += ", " + player.name
          }
        }
      }
      
      if (winner == "None") {
        return winner
      }
      else {
        if (winner.contains(",")) {
          winner += " TIED!"
        }
        else {
          winner += " is the winner!"
        }
      }
      return winner
  }
  
  def takeDealerTurn() = {
    //TODO implement logic for this.  How can I make the dealer take a turn?  Add him
    // to the player order in the playerQueue?  This will create a problem with display
    // as it is.  I'll have to develop logic to exclude the dealer from showing 
    // in the player hands?
  }
}
