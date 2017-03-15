package blackjack

import model._
import view._
import controller._

import org.scalatest.FunSpec
import org.scalatest.Matchers
import scala.collection.mutable.ArrayBuffer

class milestone_1_tests extends FunSpec with Matchers {
   describe("Milestone 1 tests") {
    describe("Displays the initial player order") {
      it("Checks that the intial player order is 'Alice, Bob, Carl, Doug'") {

        val expectedPlayerOrder = "DealNewHands, Alice, Bob, Carl, Doug, Dealer, Payout, CheckForWinner"

        ActionQueue.showPlayerOrder should equal(expectedPlayerOrder)
      }
    }
    describe("Advances the turn and checks the player order") {
      it("Checks that the new player order is 'Bob, Carl, Doug, Alice'"){
        val expectedOrderTurn1 = "DealNewHands, Alice, Bob, Carl, Doug, Dealer, Payout, CheckForWinner"
        val expectedOrderTurn2 = "Alice, Bob, Carl, Doug, Dealer, Payout, CheckForWinner, DealNewHands"
        val expectedOrderTurn3 = "Bob, Carl, Doug, Dealer, Payout, CheckForWinner, DealNewHands, Alice"
        val expectedOrderTurn4 = "Carl, Doug, Dealer, Payout, CheckForWinner, DealNewHands, Alice, Bob"
        val expectedOrderTurn5 = "Doug, Dealer, Payout, CheckForWinner, DealNewHands, Alice, Bob, Carl"
        val expectedOrderTurn6 = "Dealer, Payout, CheckForWinner, DealNewHands, Alice, Bob, Carl, Doug"
        val expectedOrderTurn7 = "Payout, CheckForWinner, DealNewHands, Alice, Bob, Carl, Doug, Dealer"
        val expectedOrderTurn8 = "CheckForWinner, DealNewHands, Alice, Bob, Carl, Doug, Dealer, Payout"
        val expectedOrderTurn9 = "DealNewHands, Alice, Bob, Carl, Doug, Dealer, Payout, CheckForWinner"
        
        ActionQueue.showPlayerOrder shouldBe(expectedOrderTurn1)
        ActionQueue.advanceActionOrder
        ActionQueue.showPlayerOrder shouldBe(expectedOrderTurn2)
        ActionQueue.advanceActionOrder
        ActionQueue.showPlayerOrder shouldBe(expectedOrderTurn3)
        ActionQueue.advanceActionOrder
        ActionQueue.showPlayerOrder shouldBe(expectedOrderTurn4)
        ActionQueue.advanceActionOrder
        ActionQueue.showPlayerOrder shouldBe(expectedOrderTurn5)
        ActionQueue.advanceActionOrder
        ActionQueue.showPlayerOrder shouldBe(expectedOrderTurn6)
        ActionQueue.advanceActionOrder
        ActionQueue.showPlayerOrder shouldBe(expectedOrderTurn7)
        ActionQueue.advanceActionOrder
        ActionQueue.showPlayerOrder shouldBe(expectedOrderTurn8)
        ActionQueue.advanceActionOrder
        ActionQueue.showPlayerOrder shouldBe(expectedOrderTurn9)

        
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
        expectedResult += "Money: $95          Money: $95          Money: $95          Money: $95          "
        expectedResult += "Stragegy: Dealer    Stragegy: Dealer    Stragegy: Dealer    Stragegy: Dealer    "
        expectedResult += "Next Action: Alice"
        
        Dealer2.initializeGame(true)
        
        val t = new textView()
        
        assert(t.showGameArea.equals(expectedResult) == true, "\n" +t.showGameArea.toString + "\n" + expectedResult)
        
      }
    }
  }
  describe("Class Validation Tests"){
      describe("This test suite validates class structures are working as designed"){
        it("Validates the Hand class is correctly displaying"){
          val expectedValue = new ArrayBuffer[String]()
          expectedValue += "2C 3C 4C 5C         "
          expectedValue += "Hand total: 14      "
          expectedValue += "                    "
          
          var h = new Hand()
          var deck = new Deck()
          
          deck.initializeDeck(true)
          
          h.cards += deck.deal()
          h.cards += deck.deal()
          h.cards += deck.deal()
          h.cards += deck.deal()
         
          val handStringArray = h.printHand()
          
  
          
          handStringArray shouldBe(expectedValue)
      }
        it("Validates the Player class is correctly displaying") {
          val expectedValue = new ArrayBuffer[String]()
          expectedValue += "Bet: $5             "
          expectedValue += "                    "
          expectedValue += "2C 3C 4C 5C         "
          expectedValue += "Hand total: 14      "
          expectedValue += "                    "
          expectedValue += "test player         "
          expectedValue += "Money: $100         "
          expectedValue += "Strategy: Dealer    "
          
     
          var h = new Hand()
          var deck = new Deck()
          
          deck.initializeDeck(true)
          
          h.cards += deck.deal()
          h.cards += deck.deal()
          h.cards += deck.deal()
          h.cards += deck.deal()
         
          var player = new Player("test player", 100)
          player.hands += h
          
          val playerArray = player.printPlayer()
                      
          playerArray shouldBe(expectedValue)
        }
        
        it("Validates the Dealer class is correctly displaying") {
          var h = new Hand()
          var deck = new Deck()
          
          deck.initializeDeck(true)
          
//          Dealer.dealerHand.cards += Dealer.deck.deal()
//          Dealer.dealerHand.cards += Dealer.deck.deal()
//          Dealer.dealerHand.cards += Dealer.deck.deal()
//          Dealer.dealerHand.cards += Dealer.deck.deal()
//          
//          val playerArray = Dealer.printDealer()
//          
//          val expectedValue = new ArrayBuffer[String]()
//          expectedValue += "2C 3C 4C 5C         "
//          expectedValue += "Hand total: 14      "
//          expectedValue += "                    "
//          expectedValue += "Dealer              "
//        
//          playerArray shouldBe(expectedValue)
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
          expectedResult += "Stragegy: Dealer    Stragegy: Dealer    Stragegy: Dealer    Stragegy: Dealer    "
          expectedResult += "Next Action: Bob"

      }
    }
  }
}
