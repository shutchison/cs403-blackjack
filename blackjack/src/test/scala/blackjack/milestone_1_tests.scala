package blackjack

import org.scalatest.FunSpec
import org.scalatest.Matchers

class milestone_1_tests extends FunSpec with Matchers {
   describe("Milestone 1 tests") {
    describe("Displays the initial player order") {
      it("Checks that the intial player order is 'Alice, Bob, Carl, Doug, Dealer'") {

        val expectedPlayerOrder = "Alice, Bob, Carl, Doug, Dealer"
        
        val t = new Table()
        t.newHand(true)
        t.showPlayerOrder should equal(expectedPlayerOrder)

        
      }
    }
    describe("Advances the turn and check the player order") {
      it("Checks that the new player order is 'Bob, Carl, Doug, Dealer, Alice'"){
        val expectedPlayerOrderTurnTwo = "Bob, Carl, Doug, Dealer, Alice"
        
        val t = new Table()
        t.newHand(true)
        t.advanceOrder()
        t.showPlayerOrder() should equal(expectedPlayerOrderTurnTwo)
      }
    }
    describe("Checks that the board is diplayed correctly."){
      it("Loads the default deck of cards and checks that it is properly displayed."){
        val boardString = """
----------------------------------------------------------------------------------------------------
|                                                                                                  |
|                                                BLACKJACK!                                        |
|                                                                                                  |
|                                                                                                  |
|                                                10S 4C                                            |
|                                                Dealer                                            |
|                                                Total: 14                                         |
|                                                                                                  |
|                                                                                                  |
|         5H 7D                  2D 4S                  AS AH                  4D 2S               |
|         Doug                   Carl                   Bob                    Alice               |
|         Total: 12              Total: 6               Total: 22              Total: 6            |
|         Money: 100             Money: 100             Money: 100             Money: 100          |
|Player:                                                                       *                   |
----------------------------------------------------------------------------------------------------
"""
        val t = new Table()
        t.newHand(true)
        t.showGameArea() should equal(boardString.trim)
      }
    }
        
  }
}
