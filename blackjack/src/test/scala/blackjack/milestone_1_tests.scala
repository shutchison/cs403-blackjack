package blackjack

import org.scalatest.FunSpec
import org.scalatest.Matchers
import scala.collection.mutable.ArrayBuffer

class milestone_1_tests extends FunSpec with Matchers {
   describe("Milestone 1 tests") {
    describe("Displays the initial player order") {
      it("Checks that the intial player order is 'Alice, Bob, Carl, Doug'") {

        val expectedPlayerOrder = "Alice, Bob, Carl, Doug"
        
        val q = new PlayerQueue()
        q.showPlayerOrder() should equal(expectedPlayerOrder)
      }
    }
    describe("Advances the turn and check the player order") {
      it("Checks that the new player order is 'Bob, Carl, Doug, Alice'"){
        val expectedPlayerOrderTurnTwo = "Bob, Carl, Doug, Alice"
        
        val q = new PlayerQueue()
        q.advanceOrder()
        q.showPlayerOrder() should equal(expectedPlayerOrderTurnTwo)
      }
    }
    
    describe("Checks that the board is diplayed correctly."){
      it("Loads the default deck of cards and checks that it is properly displayed."){
        var expectedResult = new ArrayBuffer[String]()
        expectedResult += "================================================================================"
        expectedResult += "                                   BLACKJACK!"
        expectedResult += "================================================================================"
        expectedResult += "                                                                                "
        expectedResult += "                                   6C JC               "
        expectedResult += "                                   Hand total: 16      "
        expectedResult += "                                                       "
        expectedResult += "                                   Dealer              "              
        expectedResult += "                                                                                "
        expectedResult += "--------------------------------------------------------------------------------"
        expectedResult += "Bet: $5             Bet: $5             Bet: $5             Bet: $5             "
        expectedResult += "                                                                                "
        expectedResult += "5C 10C              4C 9C               3C 8C               2C 7C               "
        expectedResult += "Hand total: 15      Hand total: 13      Hand total: 11      Hand total: 9       "
        expectedResult += "                                                                                "
        expectedResult += "Doug                Carl                Bob                 Alice               "
        expectedResult += "Money: $100         Money: $100         Money: $100         Money: $100         "

        var dealer = new Dealer()
        dealer.dealNewHands(true)
    
        Blackjack.showGameArea(dealer) shouldBe expectedResult
        
      }
    }
  }
  describe("Class Validation Tests"){
      describe("This test suite validates class structures are working as designed"){
        it("Validates the Hand class is returning the correct result"){
            var h = new Hand()
            var deck = new Deck()
            
            deck.initializeDeck(true)
            
            h.cards += deck.deal()
            h.cards += deck.deal()
            h.cards += deck.deal()
            h.cards += deck.deal()
           
            val handStringArray = h.printHand()
            
            val expectedValue = new ArrayBuffer[String]()
            expectedValue += "2C 3C 4C 5C         "
            expectedValue += "Hand total: 14      "
            expectedValue += "                    "
            
            handStringArray shouldBe(expectedValue)
      }
        it("Validates the Player class is correctly displaying") {
              var h = new Hand()
              var deck = new Deck()
              
              deck.initializeDeck(true)
              
              h.cards += deck.deal()
              h.cards += deck.deal()
              h.cards += deck.deal()
              h.cards += deck.deal()
             
              var player = new Player("test player")
              player.hands += h
              
              val playerArray = player.printPlayer()
              
              val expectedValue = new ArrayBuffer[String]()
              expectedValue += "Bet: $5             "
              expectedValue += "                    "
              expectedValue += "2C 3C 4C 5C         "
              expectedValue += "Hand total: 14      "
              expectedValue += "                    "
              expectedValue += "test player         "
              expectedValue += "Money: $100         "
            
              playerArray shouldBe(expectedValue)
        }
        
        it("Validates the Dealer class is correctly displaying") {
              var h = new Hand()
              var deck = new Deck()
              
              deck.initializeDeck(true)
              
              h.cards += deck.deal()
              h.cards += deck.deal()
              h.cards += deck.deal()
              h.cards += deck.deal()
             
              var player = new Dealer()
              player.dealerHand = h
              
              val playerArray = player.printDealer()
              
              val expectedValue = new ArrayBuffer[String]()
              expectedValue += "2C 3C 4C 5C         "
              expectedValue += "Hand total: 14      "
              expectedValue += "                    "
              expectedValue += "Dealer              "
            
              playerArray shouldBe(expectedValue)
        }
        it("Checks that advancing the turn does not alter player order when the board is displayed") {
        var expectedResult = new ArrayBuffer[String]()
        expectedResult += "================================================================================"
        expectedResult += "                                   BLACKJACK!"
        expectedResult += "================================================================================"
        expectedResult += "                                                                                "
        expectedResult += "                                   6C JC               "
        expectedResult += "                                   Hand total: 16      "
        expectedResult += "                                                       "
        expectedResult += "                                   Dealer              "              
        expectedResult += "                                                                                "
        expectedResult += "--------------------------------------------------------------------------------"
        expectedResult += "Bet: $5             Bet: $5             Bet: $5             Bet: $5             "
        expectedResult += "                                                                                "
        expectedResult += "5C 10C              4C 9C               3C 8C               2C 7C               "
        expectedResult += "Hand total: 15      Hand total: 13      Hand total: 11      Hand total: 9       "
        expectedResult += "                                                                                "
        expectedResult += "Doug                Carl                Bob                 Alice               "
        expectedResult += "Money: $100         Money: $100         Money: $100         Money: $100         "
        
        var dealer = new Dealer()
        dealer.dealNewHands(true)
    
        dealer.playerQueue.advanceOrder()
        Blackjack.showGameArea(dealer) shouldBe(expectedResult)
      }
    }
  }
}
