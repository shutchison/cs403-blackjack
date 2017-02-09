package blackjack

import org.scalatest.FunSpec
import org.scalatest.Matchers
import scala.collection.mutable.ArrayBuffer

class ms3_tests extends FunSpec with Matchers{
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
      expectedResult += "Money: $95          Money: $95          Money: $95          Money: $95          "
      expectedResult += "Current Player: Alice"
      
      Blackjack.initializeGame()
      
      var actualResult = Blackjack.showGameArea()

      assert(actualResult.equals(expectedResult) == true, "\n" +actualResult.toString + "\n" + expectedResult)
      
    }
    it("Confirms that winners are detected") {
      Blackjack.initializeGame()
      Dealer.dealNewHands(true)
      
      var winnerString = Dealer.checkForWinner()
      winnerString shouldBe("None")
      
      Dealer.playerQueue.players(0).money = 199
      winnerString = Dealer.checkForWinner()
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
    it("Does a move and blah blah blah") {
      
    }
  }

  describe("Class Validation Tests"){
    describe("This test suite validates class structures are working as designed"){
      it("Validates that aces are being correctly counted as either 11 or 1"){
        var expectedResult = new ArrayBuffer[String]()
    
        expectedResult += "Bet: $5             "
        expectedResult += "                    "
        expectedResult += "3D AD               "
        expectedResult += "Hand total: 14      "
        expectedResult += "                    "
        expectedResult += "tester              "
        expectedResult += "Money: $100         "
        expectedResult += "===================="
        expectedResult += "Bet: $5             "
        expectedResult += "                    "
        expectedResult += "3D AD KD            "
        expectedResult += "Hand total: 14      "
        expectedResult += "                    "
        expectedResult += "tester              "
        expectedResult += "Money: $100         "
        expectedResult += "===================="
        expectedResult += "Bet: $5             "
        expectedResult += "                    "
        expectedResult += "3D AD KD AH         "
        expectedResult += "Hand total: 15      "
        expectedResult += "                    "
        expectedResult += "tester              "
        expectedResult += "Money: $100         "
        expectedResult += "===================="
        expectedResult += "Bet: $5             "
        expectedResult += "                    "
        expectedResult += "3D AD KD AH KS      "
        expectedResult += "Hand total: 25      "
        expectedResult += "*** BUSTED ***      "
        expectedResult += "tester              "
        expectedResult += "Money: $100         "
        expectedResult += "===================="
        expectedResult += "Bet: $5             "
        expectedResult += "                    "
        expectedResult += "AH AD               "
        expectedResult += "Hand total: 12      "
        expectedResult += "                    "
        expectedResult += "tester              "
        expectedResult += "Money: $100         "
        expectedResult += "===================="
    
        var actualResult = new ArrayBuffer[String]()
        
        var p = new Player("tester", 100)
          
        p.hands += new Hand()
        p.hands(0).cards += new Card("3", "D")
        p.hands(0).cards += new Card("A", "D")
    
        actualResult ++= p.printPlayer()
        actualResult += "="*20
    
        p.hands(0).cards += new Card("K", "D")
        
        actualResult ++= p.printPlayer()
        actualResult += "="*20
    
        
        p.hands(0).cards += new Card("A", "H")
    
        actualResult ++= p.printPlayer()
        actualResult += "="*20
            
        p.hands(0).cards += new Card("K", "S")
        
        actualResult ++= p.printPlayer()
        actualResult += "="*20
        
        p.hands.remove(0)
        
        p.hands += new Hand()
        p.hands(0).cards += new Card("A", "H")
        p.hands(0).cards += new Card("A", "D")
        
        actualResult ++= p.printPlayer()
        actualResult += "="*20
        
        actualResult shouldBe(expectedResult)
      }
    }
  }
}