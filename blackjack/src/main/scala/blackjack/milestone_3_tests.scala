package blackjack

import org.scalatest.FunSpec
import org.scalatest.Matchers
import scala.collection.mutable.ArrayBuffer

class milestone_3_tests  extends FunSpec with Matchers{
  describe("Milestone 3 tests") {
    describe("Checks that initialization works"){
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

      var dealer = Blackjack.initializeGame()
      dealer.dealNewHands(true)
  
      Blackjack.showGameArea(dealer) shouldBe expectedResult 
      
    }
    it("Confirms that winners are detected") {
      var dealer = Blackjack.initializeGame()
      dealer.dealNewHands(true)
      
      var winnerString = dealer.checkForWinner()
      winnerString shouldBe("None")
      
      dealer.playerQueue.players(0).money = 200
      winnerString = dealer.checkForWinner()
      winnerString shouldBe("Alice is the winner!")
      
      dealer.playerQueue.players(1).money = 200
      winnerString = dealer.checkForWinner()
      winnerString shouldBe("Alice, Bob TIED!")
      
      dealer.playerQueue.players(2).money = 200
      winnerString = dealer.checkForWinner()
      winnerString shouldBe("Alice, Bob, Carl TIED!")
      
      dealer.playerQueue.players(3).money = 200
      winnerString = dealer.checkForWinner()
      winnerString shouldBe("Alice, Bob, Carl, Doug TIED!")
    }
    it("Does a move and ") {
      
    }
  }
}