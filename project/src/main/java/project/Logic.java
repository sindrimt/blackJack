package project;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Logic {

  private int ptxt;
  private int dtxt;
  private Deck deck = new Deck();
  private Random rand = new Random();
  private List < Card > playerCards = new ArrayList < Card > (); 
  private List < Card > dealerCards = new ArrayList < Card > (); 
  private int currentBet;
  private final FileManager manager;
  private final MyController controller;
  
  public Logic(MyController controller) {
    this.controller = controller;
    manager = new FileManagerI(this);
  }
  public Deck getDeck() {
    return deck;

  }
//  Denne metoden går gjennom enten dealerCards eller playerCards, og sjekker om listen 
//  inneholder minst et kort med value 1 (ess). Returnerer true eller false 
//  (Bruker ikke)
  public boolean findAce(List < Card > cards) {
    return cards.stream().anyMatch(c -> c.getValue() == 1);
  }

//  public void continueGame() {
//    totalCash = manager.getGameData().getTotalCash();
//    System.out.println(totalCash);
//  }

  public FileManager getFileManager() {
    return manager;
  }

  public void newGame() {
    manager.getGameData().setTotalCash(100);

  }

  public List < Card > getDealerCards() {
    return dealerCards;
  }

  public List < Card > getPlayerCards() {
    return playerCards;
  }

  public int getRandomIndex() {
    return rand.nextInt(deck.getDeck().size());
  }

  public void setPtxtYes() {
    ptxt += 11;
  }

  public void setPtxtNo() {
    ptxt += 1;
  }
  
//  public ImageView getImage(int randomIndex) {
//	  return deck.getDeck().get(randomIndex).getImg();
//  }

  public void play() {

    int randomIndex = getRandomIndex();
    Card playerCard = deck.getDeck().get(randomIndex);
    //Players card
    controller.cardBox.getChildren().add(playerCard.getImg());
    playerCards.add(playerCard);
    try {
		manager.saveGameState("gameStatePlayer.txt", getPlayerCards());
	} catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

    if (playerCard.getValue() == 1) {
      controller.addAceButtons();
    }
    checkPlayerValue(playerCard.getValue());
//     else if (deck.getDeck().get(randomIndex).getValue() >= 10) setPtxt(getPtxt() + 10);
//
//    else setPtxt(getPtxt() + deck.getDeck().get(randomIndex).getValue());

    controller.playerText.setText("Your score " + getPtxt());
    deck.getDeck().remove(randomIndex);

    //Dealers card
    randomIndex = getRandomIndex();
    Card dealerCard = deck.getDeck().get(randomIndex);
    controller.dcardBox.getChildren().add(dealerCard.getImg());

    dealerCards.add(dealerCard);
    try {
		manager.saveGameState("gameStateDealer.txt", getDealerCards());
	} catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

    if (dealerCards.get(0).getValue() == 1) dtxt = 11;

    else {
    	checkDealerValue(dealerCard.getValue());
//      if (deck.getDeck().get(randomIndex).getValue() >= 10) dtxt += 10;
//
//      else dtxt += deck.getDeck().get(randomIndex).getValue();
    }
  
    updateDealerText();
    deck.getDeck().remove(randomIndex);

    checkIfEnd();
  }

  public void hit() {
	
    //Legger til nytt kort til spiller
    int randomIndex = getRandomIndex();
    Card playerCard = deck.getDeck().get(randomIndex);
    	
    if (playerCard.getValue() == 1) {

      controller.addAceButtons();

    }
    controller.cardBox.getChildren().add(playerCard.getImg());
    playerCards.add(playerCard);
    try {
		manager.saveGameState("gameStatePlayer.txt", getPlayerCards());
	} catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

//    if (deck.getDeck().get(randomIndex).getValue() >= 10) setPtxt(getPtxt() + 10);
//
//    else setPtxt(getPtxt() + deck.getDeck().get(randomIndex).getValue());
    checkPlayerValue(playerCard.getValue());

    updatePlayerText();
    deck.getDeck().remove(randomIndex);

    //Respons fra dealer om spillet fortsatt er i gang
    //Dealer hit bare om scoren hans er lik eller mindre enn 17
    if (getPtxt() < 21 && dtxt <= 17) {
      System.out.println("her vil Dealer bette");
      randomIndex = getRandomIndex();
      Card dealerCard = deck.getDeck().get(randomIndex);
      controller.dcardBox.getChildren().add(dealerCard.getImg());
      dealerCards.add(dealerCard);
      
      try {
		manager.saveGameState("gameStateDealer.txt", getDealerCards());
	} catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
      checkDealerValue(dealerCard.getValue());
//      if (deck.getDeck().get(randomIndex).getValue() >= 10) dtxt += 10;
//
//      else dtxt += deck.getDeck().get(randomIndex).getValue();
      
      updateDealerText();
      deck.getDeck().remove(randomIndex);
    }
    checkIfEnd();
  }
  public void checkDealerValue(int value) {
	  if (value < 0 ) throw new IllegalArgumentException();
	  if (value >= 10)  dtxt += 10;//Gjør om bildekort til value 10    
      else dtxt += value;//Hvis ikke, value blir satt til kortets verdi       
  }
  
  public void checkPlayerValue(int value) {
	  if (value < 0 ) throw new IllegalArgumentException();
	  if (value >= 10) setPtxt(getPtxt() + 10);
	  else setPtxt(getPtxt() + value);     
  }

  public void stay() {
//    int randomIndex = getRandomIndex();
    
    //System.out.println("Clicked Stay");
    if (getPtxt() != 0) { //Sjekker om scoren til player ikke er lik 0
      if (dtxt < 21) {
        while (dtxt <= 21) { //While loopen looper helt til dealer ikke vil ta et til hit (Om scoren er mindre eller lik 17)
          if (dtxt <= 17) {
            int randomIndex = getRandomIndex();
            Card dealerCard = deck.getDeck().get(randomIndex);
            checkDealerValue(dealerCard.getValue());
            
            controller.dcardBox.getChildren().add(dealerCard.getImg());
            dealerCards.add(dealerCard);
            try {
				manager.saveGameState("gameStateDealer.txt", getDealerCards());
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            deck.getDeck().remove(randomIndex);

            checkIfEnd();

          } else {
            break; //Hvis scoren til dealer overstiger 21, stopped while loopen
          }
        }
        updateDealerText(); //Til slutt oppdateres dealers score til ny score
      }

      /*Denne koden sjekker hvem som har vunnet etter spilleren har trykket på stay, og
      dealeren har spilt sin respons
      */
      if (dtxt <= 21 && getPtxt() < dtxt) {
        // System.out.println("Dealer wins the game LOL");
        controller.setGameText("Dealer wins the game LOL");
        saveGameLoss();
      } else if (dtxt == getPtxt()) {
        controller.setGameText("Dealer wins the game because you have the same score");
        saveGameLoss();
      } else if (getPtxt() < 21 && dtxt < 21 && getPtxt() > dtxt){
        //System.out.println("Player wins the game LOL");
        controller.setGameText("Player wins the game LOL"); 
        saveGameWin();
        System.out.println("sansgamingstay");
      }
    } else {
      //System.out.println("Can't stay on value of 0 :3");
    controller.setGameText("Can't stay on value of 0 :3");
      throw new IllegalArgumentException();
      
    }
  } // Slutt stay-metoden

  public void updatePlayerText() {
    controller.playerText.setText("Your score " + getPtxt());
  }

  public void updateDealerText() {
    controller.dealerText.setText("Dealers score " + dtxt);
  }
  
  public void updateTotalCash() {
	    controller.totalCash.setText("Total cash " + manager.getGameData().getTotalCash());
  }

  public void restart() {
    setPtxt(0);
    dtxt = 0;
    deck = new Deck();
    updatePlayerText();
    updateDealerText();
  }

  public void checkIfBetIsValid() throws IllegalArgumentException { // Sjekker om det man better ikke er større enn det man har tilgjengelig
    System.out.println(manager.getGameData().getTotalCash()); // HER
    if (controller.spinner.getValue() <= manager.getGameData().getTotalCash()) { //HER
      controller.playButton.setDisable(false);
      currentBet = controller.spinner.getValue();
      
    } else throw new IllegalArgumentException("Du kan ikke bette mindre enn du har");
  }

  public void setBet(int value) {
    currentBet = value;
  }

  public void saveGameWin() {
	updateTotalCash();
    controller.gameEnd();
    manager.deleteGameStateFiles();
    manager.getGameData().addTotalCash(currentBet);
    try {
		manager.saveGameData();
	} catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
  }

  public void saveGameLoss() {
	updateTotalCash();
    controller.gameEnd();
    manager.deleteGameStateFiles();
    manager.getGameData().addTotalCash(-currentBet);
    try {
		manager.saveGameData();
	} catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
  }
  
  public void continueGameState() { // Denne legger til kortene fra tidligere tilstand, og dems verdi
	  for (Card playerCards: manager.readGameState("gameStatePlayer.txt")) {
		  controller.cardBox.getChildren().add(playerCards.getImg());
		  
		  	if (playerCards.getValue() > 10) ptxt += 10;
		  	else ptxt += playerCards.getValue(); 
	
		  controller.playerText.setText("Your score " + getPtxt());
	  }
	  for (Card dealerCards: manager.readGameState("gameStateDealer.txt")) {
		  controller.dcardBox.getChildren().add(dealerCards.getImg());
		  
		  	if (dealerCards.getValue() > 10) dtxt += 10;
		  	else dtxt += dealerCards.getValue(); 
		  
		  controller.dealerText.setText("Dealers score " + dtxt);
	  }  
 }

  public void checkIfEnd() { //Denne sjekker bare status i spillet
    if (dtxt == 21) {
      //System.out.println("Dealer wins the game by BackJack");
      controller.setGameText("Dealer wins the game by BackJack");
      saveGameLoss();
      //return false;
    } else if (getPtxt() > 21) {
      //System.out.println("Dealer wins the game by you getting over 21");
      controller.setGameText("Dealer wins the game by you getting over 21");
      saveGameLoss();
    } else if (getPtxt() < 21 && dtxt > 21) {
      //System.out.println("Player wins by dealer busting and not player");
      controller.setGameText("Player wins by dealer busting and not player");
      saveGameWin();
      System.out.println("sans");
    } else {
      //System.out.println("The game is not over yet");
      controller.setGameText("The game is not over yet");
      return;
    }
  }

  public int getCurrentBet() {
	  return currentBet;
  }
  
  public int getPtxt() {
    return ptxt;
  }
  
  public int getDtxt() {
	    return dtxt;
	  }

  public void setPtxt(int ptxt) {
    this.ptxt = ptxt;
  }
}