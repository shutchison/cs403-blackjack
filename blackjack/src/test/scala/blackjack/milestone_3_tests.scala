package blackjack

import org.scalatest.FunSpec
import org.scalatest.Matchers
import scala.collection.mutable.ArrayBuffer

class milestone_3_tests extends FunSpec with Matchers{
  println("test")
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

      Blackjack.initializeGame()
      Dealer.dealNewHands(true)
  
      Blackjack.showGameArea() shouldBe expectedResult 
      
    }
    it("Confirms that winners are detected") {
      Blackjack.initializeGame()
      Dealer.dealNewHands(true)
      
      var winnerString = Dealer.checkForWinner()
      winnerString shouldBe("None")
      
      Dealer.playerQueue.players(0).money = 200
      winnerString = Dealer.checkForWinner()
      winnerString shouldBe("Alice is the winner!")
      
      Dealer.playerQueue.players(1).money = 200
      winnerString = Dealer.checkForWinner()
      winnerString shouldBe("Alice, Bob TIED!")
      
      Dealer.playerQueue.players(2).money = 200
      winnerString = Dealer.checkForWinner()
      winnerString shouldBe("Alice, Bob, Carl TIED!")
      
      Dealer.playerQueue.players(3).money = 200
      winnerString = Dealer.checkForWinner()
      winnerString shouldBe("Alice, Bob, Carl, Doug TIED!")
    }
    it("Does a move and ") {
      
    }
  }
}
