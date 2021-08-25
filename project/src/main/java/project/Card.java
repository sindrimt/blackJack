package project;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Card {

  private char suit;
  private int value;

  public Card(char suit, int value) {
    this.suit = suit;
    this.value = value;
  }

  public ImageView getImg() {
    //System.out.println("images/" + suit + value + ".png");
    return new ImageView(new Image("images/" + suit + value + ".png")); 
  }

  public Card() {}

  public char getSuit() {
    return suit;
  }

  public void setSuit(char suit) {
    this.suit = suit;
  }

  public int getValue() {
    return value;
  }

  public void setValue(int value) {
    this.value = value;
  }

  @Override
  public String toString() {
    return "\n" + value + suit;
  }

}