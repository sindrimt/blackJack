package project;

import java.util.ArrayList;

public class Deck {
  ArrayList < Card > cards = new ArrayList < Card > ();

  int[] values = {
    1,
    2,
    3,
    4,
    5,
    6,
    7,
    8,
    9,
    10,
    11,
    12,
    13
  };
  char[] suits = {
    'C',
    'S',
    'D',
    'H'
  };

  static boolean firstThread = true;
  public Deck() {
    for (int value: values) {
      for (char suit: suits) {
        this.cards.add(new Card(suit, value));
      }
    }
    
  }
  
  public int getDeckSize() {
	  return cards.size();
  }
  public ArrayList < Card > getDeck() {
    return cards;
  }

  public static void main(String[] args) {
    Deck deck = new Deck();

    //print out the deck.
    System.out.println(deck.getDeck());
  }

}