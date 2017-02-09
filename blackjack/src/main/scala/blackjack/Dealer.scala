package blackjack

import scala.collection.mutable.ArrayBuffer
import scala.util.control.Breaks._

object Dealer{
  val playerStartingMoney = 100
  
  var deck = new Deck()
  var playerQueue = new PlayerQueue()
  
  var numActivePlayers = playerQueue.players.length
  
  var lastPlayer = playerQueue.getLastPlayer()
  
  var dealerHand = new Hand()
  
  var dealerNeedsToTakeTurnNext = false
  
  
  def dealNewHands(loadFromFile: Boolean = false) = {
    // Remove all players who can't afford the minimum bet of $5
    breakable {
      for (player <- playerQueue.players) {
        if (player.money < 5) {
          playerQueue.removePlayer(player)
          numActivePlayers = playerQueue.players.length
          lastPlayer = playerQueue.getLastPlayer()
          break
        }
      }
    }

    // bets are placed and all hands are emptied
    for (player <- playerQueue.players) {
      player.money -= player.decideBet()
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
      
      //TODO: need to implement highest value is the winner and tie in case of money is equal
      //Also, need to have a winner if there is only one player remaining...
      
      
      if (playerQueue.players.length == 1) {
        return playerQueue.players(0).name + " is the winner!"
      }
      
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
    println("Dealer is taking his turn")
    while (dealerHand.getHandValue() < 17) {
      dealerHand.cards += deck.deal()
    }
    payWinners()
    dealerNeedsToTakeTurnNext = false
    playerQueue.restartQueue()
  }

  def doMove() : String = {
    // doMove will solicit one move from the active player, or cause the dealer to take his whole turn and pay the winners.
    val currentPlayer = playerQueue.getCurrentPlayer()

    if (dealerNeedsToTakeTurnNext) {
      playerQueue.advanceOrder()
      takeDealerTurn()
      return "Dealer turn"
    }
    else {      
      val playerMove = currentPlayer.solicitDecision()
      
      //TODO: This is going to not work if the last player has multiple hands probably...  It may work now... need to test this.
      if (currentPlayer.equals(lastPlayer) == true && (playerMove == "stand" || playerMove == "bust")) {
        if (currentPlayer.isLastHandToPlay()) {
          dealerNeedsToTakeTurnNext = true
        }
      }
      
      playerMove match {
        case "hit" => hit()
        case "stand" => playerQueue.advanceOrder()
        case "bust" => playerQueue.advanceOrder()
        
        case unexpectedCase => println("Unexpected case: " + unexpectedCase.toString)  
      }
      
      return playerMove      
    }
  }
  
  def doTurn() = {
    // doTurn will finish the round until the dealers hand is resolved and pay the winners.
    while (!dealerNeedsToTakeTurnNext) {
      doMove()
    }
    // next doMove will trigger dealer's turn
    doMove()

    var gameLines = Blackjack.showGameArea()
    for (line <- gameLines) {
      println(line)
    }
  }
  
  def doGame(loadFromFile: Boolean = false) = {
    var winnerString = "None"
    while (winnerString == "None"){
      doTurn()
      winnerString = checkForWinner()
      println("Winner string is: " + winnerString) 
      dealNewHands(loadFromFile)
    }
  }
  
  def payWinners() = {
    //TODO: pay blackjacks 3:2 here
    println("Paying winners!")
    var handResultString = ""
    var result = ""
    
    for (player <- playerQueue.players) {
      // Dealer busted, so pay players who haven't busted.
      if (dealerHand.getHandValue() > 21) {
        for (hand <- player.hands) {
          if (hand.getHandValue() == 21 && hand.cards.length == 2) {
            player.money += player.bet*3/2
            result = "BLACKJACK"
          }
          else if (hand.getHandValue() <= 21) {
            player.money += player.bet*2
            result = "WIN"
          }
          else {
            result = "LOSE"
          }
        }
      }
      //Dealer didn't bust, so pay players that beat the dealer.
      else {
        for (hand <- player.hands) {
          if (hand.getHandValue() > 21) {
            result = "BUSTED"
          }
          else if (hand.getHandValue() > dealerHand.getHandValue()) {
            player.money += player.bet*2
            result = "WIN"
          }
          else if (hand.getHandValue() == dealerHand.getHandValue()) {
            player.money += player.bet
            result = "PUSH"
          }
          else {
            result = "LOSE"
          }
        }
      }
      handResultString = result + " "*(20-result.length()) + handResultString
    }
    println("Hand results are as follows:")
    println(handResultString)
  }
}
