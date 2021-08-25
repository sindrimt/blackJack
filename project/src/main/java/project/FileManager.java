package project;

import java.io.FileNotFoundException;
import java.util.List;

public interface FileManager {

	void saveGameData() throws FileNotFoundException;

	GameData getGameData();
	
	void saveGameState(String fileName, List<Card> cardList) throws FileNotFoundException;
		
	List<Card> readGameState(String fileName);
	
	void deleteGameStateFiles();
	
	boolean checkIfFileExists();
	
	void saveCurrentBet(String filename) throws FileNotFoundException;
	
	void readCurrentBet(String filename);

	//void saveGameData(String filename) throws FileNotFoundException;

	
}
