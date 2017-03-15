package blackjack

import model._
import view._
import controller._

import org.scalatest.FunSpec
import org.scalatest.Matchers
import scala.collection.mutable.ArrayBuffer

class ms3_tests extends FunSpec with Matchers{
  describe("Milestone 3 tests") {
    describe("Checks that initialization works when loading the default deck"){
      it("Allows initialization of the game") {
        var expectedResult = ArrayBuffer(
            "================================================================================",
            "                                   BLACKJACK!",
            "================================================================================",
            "                                                                                ",
            "                                   6C JC               ",
            "                                   Hand total: 16      ",
            "                                                       ",
            "                                   Dealer              ",
            "                                                                                ",
            "--------------------------------------------------------------------------------",
            "Bet: $5             Bet: $5             Bet: $5             Bet: $5             ",
            "                                                                                ",
            "5C 10C              4C 9C               3C 8C               2C 7C               ",
            "Hand total: 15      Hand total: 13      Hand total: 11      Hand total: 9       ",
            "                                                                                ",
            "Doug                Carl                Bob                 Alice               ",
            "Money: $95          Money: $95          Money: $95          Money: $95          ",
            "Strategy: Dealer    Strategy: Dealer    Strategy: Dealer    Strategy: Dealer    ",
            "Next Action: Alice")
        
        Dealer.initializeGame(true)
        
        val t = new textView()
        
        var actualResult = t.showGameArea()

        assert(actualResult.equals(expectedResult) == true, "\n" +actualResult.toString + "\n" + expectedResult)        
      }
    }
    describe("Checks that winners are detected") {
      it("Confirms that winners are detected") {
        Dealer.initializeGame(true)
        
        
        var winnerString = Dealer.checkForWinner()
        winnerString shouldBe("None")
        
        ActionQueue.playerMap("Alice").money = 199
        winnerString = Dealer.checkForWinner()
        winnerString shouldBe("None")
        
        ActionQueue.playerMap("Alice").money = 200
        winnerString = Dealer.checkForWinner()
        winnerString shouldBe("Alice is the winner!")
        
        ActionQueue.playerMap("Bob").money = 200
        winnerString = Dealer.checkForWinner()
        winnerString shouldBe("Alice, Bob TIED!")
        
        ActionQueue.playerMap("Carl").money = 200
        winnerString = Dealer.checkForWinner()
        winnerString shouldBe("Alice, Bob, Carl TIED!")
        
        ActionQueue.playerMap("Doug").money = 200
        winnerString = Dealer.checkForWinner()
        winnerString shouldBe("Alice, Bob, Carl, Doug TIED!")
        
        Dealer.initializeGame(true)
      }
    }
    describe("Allows players to move, a whole turn can be completed, and a whole game can be completed.") {
      it("Allows players to move") {
        // Default behavior is to hit until 17 and then stand
        var expectedResult = ArrayBuffer(
            "================================================================================",
            "                                   BLACKJACK!",
            "================================================================================",
            "                                                                                ",
            "                                   6C JC               ",
            "                                   Hand total: 16      ",
            "                                                       ",
            "                                   Dealer              ",
            "                                                                                ",
            "--------------------------------------------------------------------------------",
            "Bet: $5             Bet: $5             Bet: $5             Bet: $5             ",
            "                                                                                ",
            "5C 10C              4C 9C               3C 8C               2C 7C QC            ",
            "Hand total: 15      Hand total: 13      Hand total: 11      Hand total: 19      ",
            "                                                                                ",
            "Doug                Carl                Bob                 Alice               ",
            "Money: $95          Money: $95          Money: $95          Money: $95          ",
            "Strategy: Dealer    Strategy: Dealer    Strategy: Dealer    Strategy: Dealer    ",
            "Next Action: Bob")

                
        Dealer.initializeGame(true)
        
        println(ActionQueue.showPlayerOrder)
        
        Dealer.doMove() //Alice hits
        Dealer.doMove() //Alice stands
        
        val t = new textView()
        var actualResult = t.showGameArea()
        

        
        assert(actualResult.equals(expectedResult) == true, "\n" +actualResult.toString + "\n" + expectedResult)

      }
      
      it("Allows a whole turn to finish when the dealer completes his turn and pays the players.") {
        var expectedResult = ArrayBuffer(
        "================================================================================",
        "                                   BLACKJACK!",
        "================================================================================",
        "                                                                                ",
        "                                   6C JC 5D            ",
        "                                   Hand total: 21      ",
        "                                                       ",
        "                                   Dealer              ",
        "                                                                                ",
        "--------------------------------------------------------------------------------",
        "Bet: $5             Bet: $5             Bet: $5             Bet: $5             ",
        "                                                                                ",
        "5C 10C 4D           4C 9C AC 2D 3D      3C 8C KC            2C 7C QC            ",
        "Hand total: 19      Hand total: 19      Hand total: 21      Hand total: 19      ",
        "                                                                                ",
        "Doug                Carl                Bob                 Alice               ",
        "Money: $95          Money: $95          Money: $100         Money: $95          ",
        "Strategy: Dealer    Strategy: Dealer    Strategy: Dealer    Strategy: Dealer    ",
        "Next Action: DealNewHands")
        
        Dealer.initializeGame(true)
        
        Dealer.doTurn(true, false)
        
        val t = new textView()
        var actualResult = t.showGameArea()

        
        assert(actualResult.equals(expectedResult) == true, "\n" +actualResult.toString + "\n" + expectedResult)
      }
      /*it("Allows an entire game to be played") {
        var expectedResult = new ArrayBuffer[String]()
        expectedResult += "================================================================================"
        expectedResult += "                                   BLACKJACK!"
        expectedResult += "================================================================================"
        expectedResult += "                                                                                "
        expectedResult += "                                   3C 5C               "
        expectedResult += "                                   Hand total: 8       "
        expectedResult += "                                                       "
        expectedResult += "                                   Dealer              "
        expectedResult += "                                                                                "
        expectedResult += "--------------------------------------------------------------------------------"
        expectedResult += "Bet: $5             "
        expectedResult += "                    "
        expectedResult += "2C 4C               "
        expectedResult += "Hand total: 6       "
        expectedResult += "                    "
        expectedResult += "Bob                 "
        expectedResult += "Money: $100         "
        expectedResult += "Stragegy: Dealer    "
        expectedResult += "Next Action: Bob"
        
        Dealer.initializeGame(true)

        Dealer.doGame(true, false)

        val t = new textView()
        var actualResult = t.showGameArea()
       
        assert(actualResult.equals(expectedResult) == true, "\n" +actualResult.toString + "\n" + expectedResult)
      }*/
    }
  }

  /*describe("Class Validation Tests"){
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
        expectedResult += "Stragegy: Dealer    "
        expectedResult += "===================="
        expectedResult += "Bet: $5             "
        expectedResult += "                    "
        expectedResult += "3D AD KD            "
        expectedResult += "Hand total: 14      "
        expectedResult += "                    "
        expectedResult += "tester              "
        expectedResult += "Money: $100         "
        expectedResult += "Stragegy: Dealer    "
        expectedResult += "===================="
        expectedResult += "Bet: $5             "
        expectedResult += "                    "
        expectedResult += "3D AD KD AH         "
        expectedResult += "Hand total: 15      "
        expectedResult += "                    "
        expectedResult += "tester              "
        expectedResult += "Money: $100         "
        expectedResult += "Stragegy: Dealer    "
        expectedResult += "===================="
        expectedResult += "Bet: $5             "
        expectedResult += "                    "
        expectedResult += "3D AD KD AH KS      "
        expectedResult += "Hand total: 25      "
        expectedResult += "*** BUSTED ***      "
        expectedResult += "tester              "
        expectedResult += "Money: $100         "
        expectedResult += "Stragegy: Dealer    "
        expectedResult += "===================="
        expectedResult += "Bet: $5             "
        expectedResult += "                    "
        expectedResult += "AH AD               "
        expectedResult += "Hand total: 12      "
        expectedResult += "                    "
        expectedResult += "tester              "
        expectedResult += "Money: $100         "
        expectedResult += "Stragegy: Dealer    "
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
  }*/
}