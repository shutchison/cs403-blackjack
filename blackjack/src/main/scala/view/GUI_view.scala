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

class GUI_view {
  
  val dealerPanel = new BorderPanel {
    layout += new Label("Dealer") -> North
    
  }
  
  val playerPanel = new FlowPanel {
    
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
      case ButtonClicked(component) if component == doTurnButton =>
        println("doTurn")
      case ButtonClicked(component) if component == doGameButton =>
        println("doGame")
    }
  }
  
  
  val gameArea = new BoxPanel(Orientation.Vertical) {
    contents += dealerPanel
    contents += playerPanel
    contents += buttonPanel
  }
  
  //******* MainFrame *******   
  val frame = new MainFrame {
    title = "Card Game"
    contents = gameArea
    centerOnScreen
    visible = true    
  }
}