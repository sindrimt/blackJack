package project;

import java.io.FileNotFoundException;

import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class MyController {
  public Button playButton;
  public Button stayButton;
  public Button hitButton;
  public Button playAgain;
  public Text playerText;
  public Text dealerText;
  public HBox cardBox;
  public HBox dcardBox;
  public int ptxt;
  public Pane BGPane;
  public Text gameText;
  public Button okButton;
  public Text currentBet;
  public Text totalCash;
  public AnchorPane ap;
  public Pane welcomePane;
  public Spinner < Integer > spinner;
  private final Logic logikk = new Logic(this);

  @FXML
  void clickPlay(ActionEvent event) {
    totalCash.setText("Total cash: " + logikk.getFileManager().getGameData().getTotalCash()); //HER
    logikk.setBet(spinner.getValue());
    hitButton.setDisable(false);
    stayButton.setDisable(false);
    spinner.setVisible(false);
    okButton.setVisible(false);
    playButton.setDisable(true); //Disabler playButton
    logikk.play(); //Kjører play metoden fra logikk-klassen
  }

  @FXML
  void clickHit(ActionEvent event) {
    logikk.hit(); //Kjører hit metoden fra logikk-klassen
  }

  @FXML
  void clickRestart(ActionEvent event) {
    setGameText("");
    logikk.restart(); //Kjører restart metoden fra logikk-klassen
    logikk.getFileManager().deleteGameStateFiles();
    logikk.updateTotalCash();
    playButton.setDisable(true);
    cardBox.getChildren().clear();
    dcardBox.getChildren().clear();
    hitButton.setDisable(true);
    stayButton.setDisable(true);
    okButton.setVisible(true);
    spinner.setVisible(true);
    
  }

  @FXML
  void clickStay(ActionEvent event) {
    logikk.stay(); //Kjører stay metoden fra logikk-klassen
//    System.out.println(logikk.findAce(logikk.getDealerCards()));
//    System.out.println(logikk.findAce(logikk.getPlayerCards()));
  }

  @FXML
  void clickOk(ActionEvent event) {
    currentBet.setText("Current Bet: " + spinner.getValue());
    
    try {
    	logikk.checkIfBetIsValid(); //Kjører checkIfBetIsValid metoden fra logikk-klassen
    }
    catch (IllegalArgumentException e) {
    	setGameText(e.getLocalizedMessage()); // Egen exeption for hvis det blit catcha en feil
    }	//Denne printer ikke ut feilmeldingen, men håndteren den istedet
    
    try {
		logikk.getFileManager().saveCurrentBet("saveCurrentBet.txt");
	} catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
  }

  @FXML
  void clickNyttSpill(ActionEvent event) {
    System.out.println("nytt spill");
    logikk.newGame();
    totalCash.setText("Total Cash: " + logikk.getFileManager().getGameData().getTotalCash());
    logikk.getFileManager().deleteGameStateFiles();
    fadeOut();
  }

  @FXML
  void clickFortsettSpill(ActionEvent event) {
    totalCash.setText("Total Cash: " + logikk.getFileManager().getGameData().getTotalCash());
    logikk.getFileManager().readCurrentBet("saveCurrentBet.txt");
    currentBet.setText("Current Bet: " + logikk.getFileManager().getGameData().getCurrentBet());
    System.out.println("fortsett");
    //logikk.continueGame();
    logikk.getFileManager().readCurrentBet("saveCurrentBet.txt");
    
    
    	if(logikk.getFileManager().checkIfFileExists()) {
    		logikk.continueGameState();
    		okButton.setVisible(false);
    	    spinner.setVisible(false);
    	    hitButton.setDisable(false);
    	    stayButton.setDisable(false);
    	}
    	else {
    		System.out.println("nå kjører det som vanlig");
    	}
    fadeOut();
  }

  void fadeOut() {
    Duration wait = Duration.seconds(3);
    FadeTransition fade = new FadeTransition();

    fade.setDuration(wait);
    fade.setFromValue(10);
    fade.setToValue(0);
    fade.setAutoReverse(true);
    fade.setNode(welcomePane);

    fade.play();

    Timeline timeline = new Timeline(
      new KeyFrame(wait, e -> ap.getChildren().remove(welcomePane)));
    timeline.play();
  }

  void addAceButtons() {
	
    disableButtons();

    Button aceYesButton = new Button();
    aceYesButton.setText("Make Ace 11");
    Button aceNoButton = new Button();
    aceNoButton.setText("Keep Ace 1");

    aceYesButton.setTranslateX(240);	
    aceYesButton.setTranslateY(315);

    aceNoButton.setTranslateX(360);
    aceNoButton.setTranslateY(315);

    ap.getChildren().add(aceYesButton);
    ap.getChildren().add(aceNoButton);

    aceYesButton.setOnAction(new EventHandler < ActionEvent > () {
      @Override public void handle(ActionEvent e) {
        ap.getChildren().remove(aceYesButton);
        ap.getChildren().remove(aceNoButton);
        logikk.setPtxtYes();
        playerText.setText("Your score " + logikk.getPtxt());
        enableButtons();
      }
    });

    aceNoButton.setOnAction(new EventHandler < ActionEvent > () {
      @Override public void handle(ActionEvent e) {
        ap.getChildren().remove(aceYesButton);
        ap.getChildren().remove(aceNoButton);
        logikk.setPtxtNo();
        playerText.setText("Your score " + logikk.getPtxt());
        enableButtons();
      }
    });
    
  }
  
  void disableButtons() {
    hitButton.setDisable(true);
    stayButton.setDisable(true);
    okButton.setVisible(false);
    spinner.setVisible(false);
  }

  void enableButtons() {
    hitButton.setDisable(false);
    stayButton.setDisable(false);
    okButton.setVisible(false);
    spinner.setVisible(false);
  }

  void setBackground(ImageView image) {
    BGPane.getChildren().add(image);
  }

  void setGameText(String Text) {
    gameText.setText(Text);
  }

  void gameEnd() {
    disableButtons();
    playButton.setDisable(true); //Disabler playButton
  }
}