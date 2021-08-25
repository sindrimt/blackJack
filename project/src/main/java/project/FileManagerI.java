package project;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileManagerI implements FileManager {

  private GameData gamedata = new GameData();
  private Logic logikk;

  public FileManagerI(Logic logikk) {
    this.logikk = logikk;
    readGameData();
  }


@Override
  public void saveGameData() throws FileNotFoundException {
    try {
      PrintWriter writer = new PrintWriter("scoreFile.txt");
      writer.println(gamedata.getTotalCash());
      writer.flush();
      writer.close();
    } catch (FileNotFoundException e) {
      // TODO Auto-generated catch block
      throw e;
    }
  }
  
  public void saveGameState(String fileName, List<Card> cardList) throws FileNotFoundException { // Lagrer kortene som dealeren har spilt så langt til filen gameStateDealer.txt
	  try {
		File fileD = new File(fileName);
		if(fileD.exists()) fileD.delete();
		
		PrintWriter writer = new PrintWriter(fileName);
		for (Card card: cardList) { // Looper alle kortene i dealerCards-listen
		writer.print(card);
	}
	  writer.flush();
	  writer.close();
	} catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		throw e;
	}
  }
  
  public void saveCurrentBet(String filename) throws FileNotFoundException {
	  try {
		PrintWriter writer = new PrintWriter(filename);
		writer.println(logikk.getCurrentBet());
		writer.flush();
		writer.close();
	} catch (FileNotFoundException e) {
		throw e;
	}  
  }
  public void readCurrentBet(String filename) {
	  try {
	      File file = new File(filename);
	      if (!file.exists()) { 
	        System.out.println("no extis");
	        return;
	      }
	      Scanner in = new Scanner(new FileReader(filename));
	      while ( in .hasNext()) {
	        gamedata.setCurrentBet(Integer.parseInt( in .next()));
	        
	      } System.out.println(gamedata.getCurrentBet());
	      in .close();
	    } catch (IOException e) {
	      gamedata.setCurrentBet(20); 
	    }
   }
  
  public List<Card> readGameState(String fileName) { // Henter en liste med cards fra gameStatePlayer.txt
	  List<Card> cardList = new ArrayList<Card>();
	    try {
	      File file = new File(fileName);
	      if (!file.exists()) { // Sjekker om filen ikke eksisterer
	        return cardList; // Returnerer tom liste
	      }
	      Scanner in = new Scanner(new FileReader(fileName));
	      while ( in .hasNext()) {
	    	  String s = in.next();
		    	char c = s.charAt(s.length()-1); // Definerer "Suiten" til kortet
		    	int i = Integer.parseInt(s.substring(0, s.length()-1)); // Definerer "Valuen" til kortet
		    	cardList.add(new Card(c, i));

	      } in .close();
	    } catch (IOException e) {
	      return cardList; // Feilhåndtering for fil ikke kjøre
	    }
	    return cardList; // Returnerer liste med kort fra spill-tilstand
	  }

  public void readGameData() { //Henter totalCash fra scoreFile.txt
    try {
      File file = new File("scoreFile.txt");
      	if (!file.exists()) {
      		gamedata.setTotalCash(100);
      		return;
      	}
      Scanner in = new Scanner(new FileReader("scoreFile.txt"));
      while ( in .hasNext()) {
        gamedata.setTotalCash(Integer.parseInt( in .next()));
      } in .close();
    } catch (IOException e) {
      gamedata.setTotalCash(100); // Setter total Cash til 100 hvis filen ikke åpnes / ved feil
    }
  }
  // Sletter filene gameStateDealer.txt og gameStatePlayer.txt
  // Slik at denne kan kalles når runden er ferdig, og tilstanden skal slettes
  public void deleteGameStateFiles() {
	  File fileD = new File("gameStateDealer.txt");
	  File fileP = new File("gameStatePlayer.txt");
	  File fileC = new File("saveCurrentBet.txt");
	  fileD.delete();
	  fileP.delete();
	  fileC.delete();
  }
  
//  public void checkIfScoreFileExists() {
//	  
//  }
  
  public boolean checkIfFileExists() {
	  File fileD = new File("gameStateDealer.txt");
	  File fileP = new File("gameStatePlayer.txt");
	  	if (fileD.exists() && fileP.exists()) {
	  		return true;
	  	}
	  return false;
  }
  @Override
  public GameData getGameData() {
    return gamedata;
  }

}