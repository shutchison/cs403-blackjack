package model

class Card(val value : String, val suit : String) {
  val points = Map("2"->2, "3"->3, "4"->4, "5"->5,
                   "6"->6, "7"->7, "8"->8,"9"->9,"10"->10,
                   "J"->10,"Q"->10,"K"->10,"A"->11)
                   
  def getCardValue() : Int = {
    return points(value)
  }
}