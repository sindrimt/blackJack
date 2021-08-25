package project;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FileManagerITest {

private FileManagerI manager;
private GameData gamedata;
	
	@BeforeEach
	public void setup() {
		manager = new FileManagerI(new Logic(new MyController()));
		gamedata = new GameData();
	}
	
	@Test
	public void TestCurrentBetReadAndSave() {
		//gamedata.setCurrentBet(20);
		try {
			manager.saveCurrentBet("testfil.txt");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		manager.readCurrentBet("testfil.txt");
		assertEquals(0, gamedata.getCurrentBet());
		File file = new File("testfil.txt");
		file.delete();
	}
	
	@Test 
	public void TestSaveCurrentBet() {
		try {
			manager.saveCurrentBet("CurrentBetTest.txt");
			File file = new File("CurrentBetTest.txt");
			assertTrue(file.exists());
			
			if (file.exists()) file.delete();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@Test 
	public void TestSaveGameState() {
		try {
			manager.saveGameState("GameStateTest.txt", new ArrayList<Card>());
			File file = new File("GameStateTest.txt");
			assertTrue(file.exists());
			
			if (file.exists()) file.delete();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	

}
