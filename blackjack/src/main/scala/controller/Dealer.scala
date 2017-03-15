package controller

import blackjack._
import model._
import view._

import scala.collection.mutable.ArrayBuffer
import scala.util.control.Breaks._


object Dealer{
  val playerStartingMoney = 100
  
  var winnerDetected = false
  
  var deck = new Deck()
  var dealerHand = new Hand()
  
  def initializeGame(loadFromFile: Boolean = false) = {
    ActionQueue.reset
    winnerDetected = false
    Dealer.dealNewHands(loadFromFile)
  }  
  
  def dealNewHands(loadFromFile: Boolean = false) = {
    // Remove all players who can't afford the minimum bet of $5
    ActionQueue.removeBrokePlayers

    // bets are placed and all hands are emptied
    for (player <- ActionQueue.getPlayers) {
      player.money -= player.decideBet()
      while (player.hands.length > 0){
        player.hands.remove(0)
      }
    }
    
    
    dealerHand = new Hand()
    var newHands : ArrayBuffer[Hand] = new ArrayBuffer[Hand]()
    
    for (player <- ActionQueue.getPlayersInAlphabeticalOrder) {
      newHands += new Hand()
    } 

    deck.initializeDeck(loadFromFile)
    for (i <- 0 until 2){
      for (hand <- newHands) {
        hand.cards += deck.deal()
      }
      dealerHand.cards += deck.deal()
    }
    for (player <- ActionQueue.getPlayersInAlphabeticalOrder) {
      player.hands += newHands.remove(0)
    }
    ActionQueue.advanceActionOrder
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
  
  def doMove(loadFromFile: Boolean = false, doPrints : Boolean = false) = {
    var currentAction = ActionQueue.peekCurrentAction
    if (!winnerDetected) { //FIXME: Infinite loop here once winner is detected and doTurn is called.
      if (currentAction.isLeft) {
        var currentPlayer = currentAction.left.get
        var playerDecision = currentPlayer.solicitDecision(doPrints)
        playerDecision match {
          case "hit" => hit(currentPlayer)
          case "stand" => stand()
          case "bust" => ActionQueue.advanceActionOrder
          //TODO: double and split
        }
      
      }
      else {
        var thingToDo = currentAction.right.get
        thingToDo match {
          case "Dealer" => takeDealerTurn(doPrints)
          case "Payout" => payWinners(doPrints)
          case "DealNewHands" => dealNewHands(loadFromFile)
          case "CheckForWinner" => {
            var winnerString = checkForWinner()
            if (winnerString != "None") {
             println(winnerString) 
            }
            else {
              println("No winner detected")
            }
          }
        }
      }      
    }
    else {
      println(checkForWinner())
    }
  }
  
  
  def hit(player: Player) = {
    player.getActiveHand().cards += deck.deal
  }
  
  def stand() = {
    ActionQueue.advanceActionOrder
  }
  
  def checkForWinner() : String = {
      var winner = "None"
      
      //TODO: need to implement highest value is the winner and tie in case of money is equal
      //Also, need to have a winner if there is only one player remaining...
      
      
      if (ActionQueue.getPlayers.toList.length == 1) {
        return ActionQueue.getPlayers.toList(0).name + " is the winner!"
      }
      
      for (player <- ActionQueue.getPlayersInAlphabeticalOrder) {
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
        ActionQueue.advanceActionOrder
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
      ActionQueue.advanceActionOrder
      winnerDetected = true
      return winner
  }
  
  def takeDealerTurn(doPrints : Boolean = false) = {
    if (doPrints) {
      println("Dealer is taking his turn")
    }
    while (dealerHand.getHandValue() < 17) {
      if (doPrints) {
        println("Dealer takes a hit")
      }
      dealerHand.cards += deck.deal()
    }
    if (doPrints) {
      if (dealerHand.getHandValue() > 21) {
        println("Dealer Busts!")
      }
      else {
        println("Dealer Stands with " + dealerHand.getHandValue().toString())
      }
    }
    ActionQueue.advanceActionOrder
  }

  def doTurn(loadFromFile : Boolean = false, doPrints : Boolean = false) = {
    // doTurn will finish the round until the dealers hand is resolved and pay the winners.
    if (!winnerDetected) {
      doMove(loadFromFile, doPrints)
      var currentAction = ActionQueue.peekCurrentAction
      
      
      while(currentAction.isLeft) {
        doMove(loadFromFile, doPrints)
        
        currentAction = ActionQueue.peekCurrentAction
      }
  
      breakable {
          
  
        while(currentAction.isRight) {
          if (doPrints) {
            if(currentAction.right.get == "DealNewHands"){
              val textView = new textView()
              var gameLines = textView.showGameArea()
              for (line <- gameLines) {
              println(line)
              }
            }
          }
          
          if(currentAction.right.get == "DealNewHands"){
            break
          }
          doMove(loadFromFile, doPrints)
     
          currentAction = ActionQueue.peekCurrentAction 
        }      
      }    
  
      println(ActionQueue.showPlayerOrder)
      
    }
  }
  
  def doGame(loadFromFile: Boolean = false, doPrints: Boolean = false) = {
    var winnerString = "None"
    while (winnerString == "None"){
      doTurn(loadFromFile, doPrints)
      winnerString = checkForWinner()
      if (doPrints) {
        println("Winner string is: " + winnerString)       
      }
    }
  }
  
  def payWinners(doPrints : Boolean = false) = {
    //TODO: pay blackjacks 3:2 here
    if (doPrints) {
      println("Paying winners!")
    }
    var handResultString = ""
    var result = ""
    
    for (player <- ActionQueue.getPlayersInAlphabeticalOrder) {
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
    if (doPrints) {
      println("Hand results are as follows:")
      println(handResultString)
    }
    ActionQueue.advanceActionOrder
  }
}
