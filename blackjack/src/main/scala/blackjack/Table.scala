package blackjack

import scala.util.control.Breaks._

class Table {
  val players = Array[Player](new Player("Alice"), new Player("Bob"), new Player("Carl"), new Player("Doug"), new Player("Dealer"))
  var deck = new Deck()

  var currentPlayer = 0
  
  def newHand(loadFromFile: Boolean = false) = {
    // Deal two cards to everyone
    currentPlayer = 0
    deck.initializeDeck(loadFromFile)
    for (i <- 0 until 2){
      for (player <- players) {
        player.hand += deck.deal()
      }
    }
  }
  
  def showPlayerOrder(): String = {
    var playerString = ""
    val firstPlayerTracker = currentPlayer    
    for (i <- 0 until (players.length)){
      playerString += players(currentPlayer).name
      currentPlayer = (currentPlayer+1) % (players.length)
      if (firstPlayerTracker != currentPlayer) {
        playerString += ", "
      }
    }
    
    println(playerString)
    return playerString
  }

  def nextPlayer() = {
    var temp = players(0)
    for (i <- 0 until players.length-1){
      players(i) = players(i+1)      
    }
    players(players.length-1) = temp
  }
  
  def showGameArea() = {
    for (player <- players) {
      var cardString = ""
      for (card <- player.hand) {
        cardString += card.value + card.suit + " "
      }
    }
    
    val lineLength = 100
    val center = lineLength/2
    
    println("-"*lineLength) //top border
    println("|" + " "*(lineLength-2)+ "|") //blank line. Minus 2 compensates for outside borders
    val title = "BLACKJACK!"
    println("|" + " "*(center-2) + title + " "*(center-title.length) + "|")
    println("|" + " "*(lineLength-2)+ "|") //blank line
    println("|" + " "*(lineLength-2)+ "|") //blank line
    val dealerCards = getCardString("Dealer")
    println("|" + " "*(center-2) + dealerCards + " "*(center-dealerCards.length) + "|")
    var playerLabel = "Dealer"
    println("|" + " "*(center-2) + playerLabel + " "*(center-playerLabel.length) + "|")
    
    val dealerTotal = players(4).getHandValue().toString() // Hard coded dealer's locations here... not great...
    println("|" + " "*(center-2) + "Total: " + dealerTotal + " "*(center-dealerTotal.length-7) + "|")
    
    println("|" + " "*(lineLength-2)+ "|") //blank line
    println("|" + " "*(lineLength-2)+ "|") //blank line
    val dougHand = getCardString("Doug")
    val carlHand = getCardString("Carl")
    val bobHand = getCardString("Bob")
    val aliceHand = getCardString("Alice")
    var cardLine = "|" + " "*9
    cardLine += dougHand + " "*(23-dougHand.length)
    cardLine += carlHand + " "*(23-carlHand.length)
    cardLine += bobHand + " "*(23-bobHand.length)
    cardLine += aliceHand + " "*(20-aliceHand.length) + "|"
    
    println(cardLine)
    println("|" + " "*9 + "Doug" + " "*19 + "Carl" + " "*19 + "Bob" + " "*20 + "Alice" + " "*15 + "|")
    
    val dougTotal = players(3).getHandValue().toString()
    val carlTotal = players(2).getHandValue().toString()
    val bobTotal = players(1).getHandValue().toString()
    val aliceTotal = players(0).getHandValue().toString()
    println("|" + " "*9 + "Total: " + dougTotal + " "*(16-dougTotal.length) +
        "Total: " + carlTotal + " "*(16-carlTotal.length) +
        "Total: " + bobTotal + " "*(16-bobTotal.length) + 
        "Total: " + aliceTotal + " "*(13-aliceTotal.length) + "|")
        
    val dougMoney = players(3).money.toString()
    val carlMoney = players(2).money.toString()
    val bobMoney = players(1).money.toString()
    val aliceMoney = players(0).money.toString()
    println("|" + " "*9 + "Money: " + dougMoney + " "*(16-dougMoney.length) +
        "Money: " + carlMoney + " "*(16-carlMoney.length) +
        "Money: " + bobMoney + " "*(16-bobMoney.length) + 
        "Money: " + aliceMoney + " "*(13-aliceMoney.length) + "|") 
        
    if (currentPlayer == 0){
      println("|" + "Player: " + " "*70 + "*" + " "*19 + "|")
    }
    else if (currentPlayer == 1) {
      println("|" + "Player: " + " "*47 + "*" + " "*42 + "|")
    }
    else if (currentPlayer == 2) {
      println("|" + "Player: " + " "*24 + "*" + " "*65 + "|")
    }
    else if (currentPlayer == 3) {
      println("|" + "Player: " + " "*1 + "*" + " "*88 + "|")
    }
    else if (currentPlayer == 4) {
      println("|" + "Player: Dealer" + " "*84 + "|")
    }
    println("-"*lineLength)//bottom border
    
  }
  
  def getCardString(playerName : String):String = {
    var cardString = ""
    breakable { 
      for (player <- players) {
        if (player.name == playerName) {
            for (card <- player.hand){
              cardString += card.value + card.suit + " "
            }
            break
        }
      }
    }
    return cardString
  }
  
  def hit() = {
    players(currentPlayer).hand += deck.deal() 
  }
  
  def advanceOrder() = {
    currentPlayer = (currentPlayer+1) % (players.length)
  }
  
}

