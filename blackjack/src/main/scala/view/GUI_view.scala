package view

import blackjack._
import model._
import controller._

import scala.swing._
import scala.swing.event._
import BorderPanel.Position._
import java.awt.geom.Rectangle2D
import java.awt.geom.Ellipse2D
import java.awt.Color
import scala.collection.mutable.ArrayBuffer
import java.awt.image.BufferedImage
import scala.swing.Orientation
import javax.swing.border.LineBorder


class GUI_view {

  val dealerCardPanel = new PlayerHandPanel('h')
  dealerCardPanel.showCards(Dealer.dealerHand.cards)
  var dealerHandTextLabel = new Label("Hand total: " + Dealer.dealerHand.getHandValue())

  val dealerPanel = new BorderPanel {
    preferredSize = new Dimension(250, 200)
    
    border = new LineBorder(Color.BLACK)
    
    layout += new Label("Dealer") -> North
    layout += dealerCardPanel -> Center
    layout += dealerHandTextLabel -> South
  }

  var playerPanels = new ArrayBuffer[PlayerPanel]()
  for (player <- Dealer.playerQueue.players) {
    playerPanels += new PlayerPanel(player)
  }
    
  playerPanels = playerPanels.reverse
  
  
  val playersPanel = new BoxPanel(Orientation.Horizontal) {
    for (playerPanel <- playerPanels) {
      contents += playerPanel    
    }
  }

  val buttonPanel = new FlowPanel() {
    val doMoveButton = new Button("Do Move")
    val doTurnButton = new Button("Do Turn")
    val doGameButton = new Button("Do Game")
    contents += doMoveButton
    contents += doTurnButton
    contents += doGameButton

    listenTo(doMoveButton)
    listenTo(doTurnButton)
    listenTo(doGameButton)

    reactions += {
      case ButtonClicked(component) if component == doMoveButton =>
        println("doMove")
        Dealer.doMove()
        val t = new textView()
        var gameLines = t.showGameArea()
        for (line <- gameLines) {
          println(line)
        }
        update()
      case ButtonClicked(component) if component == doTurnButton =>
        println("doTurn")
        Dealer.doTurn(true)
        val t = new textView()
        var gameLines = t.showGameArea()
        for (line <- gameLines) {
          println(line)
        }
        update()
      case ButtonClicked(component) if component == doGameButton =>
        println("doGame")
        Dealer.doGame(true, true)
        val t = new textView()
        var gameLines = t.showGameArea()
        for (line <- gameLines) {
          println(line)
        }
        update()
    }
  }

  val gameArea = new BoxPanel(Orientation.Vertical) {
    contents += dealerPanel
    contents += playersPanel
    contents += buttonPanel
  }

  //******* MainFrame *******   
  val frame = new MainFrame {
    title = "Card Game"
    contents = gameArea
    centerOnScreen
    visible = true
  }

  //TODO: Need to get this to update everything
  def update() = {
      dealerPanel.repaint()
      playersPanel.repaint()
  }
  
  //******* CARDPANEL *******  
  class CardPanel extends Panel {

    var image = javax.imageio.ImageIO.read(new java.io.File("resources/empty.jpg"))

    def showAsEmpty {
      image = javax.imageio.ImageIO.read(new java.io.File("resources/empty.jpg"))
      this.repaint
    }

    def changeCard(card: Card) {
      image = javax.imageio.ImageIO.read(new java.io.File("resources/" + card.value + card.suit.toLowerCase() + ".jpg"))
      this.repaint
    }

    override def paint(g: Graphics2D) {
      g.drawImage(image, 18, 48, null)
    }
  }
  //******* PLAYERHANDPANEL ******* 
  class PlayerHandPanel(orientation: Char) extends Panel {

    preferredSize = new Dimension(72, 96)

    var images = new ArrayBuffer[BufferedImage]
    images += javax.imageio.ImageIO.read(new java.io.File("resources/empty.jpg"))

    def showAsEmpty {
      images.clear
      images += javax.imageio.ImageIO.read(new java.io.File("resources/empty.jpg"))
      this.repaint
    }

    def showCards(cards: ArrayBuffer[Card]) {
      images.clear
      for (card <- cards) {
        images += javax.imageio.ImageIO.read(new java.io.File("resources/" + card.value + card.suit.toLowerCase() + ".jpg"))
      }
      this.repaint
    }

    override def paint(g: Graphics2D) {
      var offset = 36
      for (image <- images) {
        if (orientation == 'v') g.drawImage(image, 0, offset, null)
        else g.drawImage(image, offset + 72, 0, null)
        offset += 30
      }
    }
  }

  class PlayerPanel (player : Player) extends BorderPanel {
    preferredSize = new Dimension(250, 200)
    var isActivePlayer = false
    
    // TODO: Get active player highlighted with red border
    if (isActivePlayer) {
      border = new LineBorder(Color.red)    
    }
    else {
      border = new LineBorder(Color.BLACK)      
    }
    
    val betLabel = new Label("Bet: $" + player.bet.toString())
    val cardPanel = new PlayerHandPanel('h')
    cardPanel.showCards(player.getActiveHand().cards)
  
    val infoPanel = new BoxPanel(Orientation.Vertical) {
      // TODO: Still need to get split working with multiple hands
      var handTextLabel = new Label("Hand total: " + player.getActiveHand().getHandValue())
      val nameLabel = new Label(player.name)
      val moneyLabel = new Label("Money: $" + player.money.toString)
  
      contents += handTextLabel
      contents += nameLabel
      contents += moneyLabel
    }

    layout += betLabel -> North
    layout += cardPanel -> Center
    layout += infoPanel -> South
    
    //TODO: Get new cards showing somehow.  This isn't working.  It seems that overriding this
    // causes it to not paint correctly the first time...
//    override def paint(g: Graphics2D) {
//      cardPanel.showCards(player.getActiveHand().cards)
//      infoPanel.handTextLabel.text = "Hand total: " + player.getActiveHand().getHandValue()
//      if (isActivePlayer) {
//        border = new LineBorder(Color.red)    
//      }
//      else {
//        border = new LineBorder(Color.BLACK)      
//      }
//    }
  }
}