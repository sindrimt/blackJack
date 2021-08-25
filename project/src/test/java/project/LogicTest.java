package project;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import project.Deck;
import project.Logic;
import project.Card;
import project.MyController;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javafx.scene.image.ImageView;

class LogicTest {
	Logic logic;
	MyController myController;
	List<Card> cards;

	@BeforeEach
	public void setUp() throws Exception {
		cards = new ArrayList();
		myController = new MyController();
		logic = new Logic(myController);

	}
	@Test
	public void testLogic() {
		assertNotNull(logic);
	}
	
//	@Test
//	public void getImageTest() {
//		assertNotNull(logic.getImage(0));
//		assertEquals(logic.getImage(0),new ImageView());
//	}
	
	@Test
	public void findAceTest() {
		cards.add(logic.getDeck().getDeck().get(3));
		cards.add(logic.getDeck().getDeck().get(6));
		cards.add(logic.getDeck().getDeck().get(7));
		cards.add(logic.getDeck().getDeck().get(19));
		
		assertTrue(logic.findAce(cards)); // Indeksene 0 - 3 er Ace i kortstokken, og vil derfor returnere true fordi 
										// i dette eksempelet er det en Ace i kortstokken
										   
		cards.clear(); // Clearer alle kortene fra kortstokken, for å kunne kjøre en ny test
		
		cards.add(logic.getDeck().getDeck().get(9));
		cards.add(logic.getDeck().getDeck().get(6));
		cards.add(logic.getDeck().getDeck().get(7));
		cards.add(logic.getDeck().getDeck().get(19));
		
		assertFalse(logic.findAce(cards));// Indeksene 0 - 3 er Ace i kortstokken, og vil derfor returnere true fordi
	}									// i dette eksempelet er det en Ace i kortstokken


	
	@Test
	public void testTotalCash() {
		assertNotNull(logic.getFileManager().getGameData().getTotalCash());
		assertTrue(logic.getFileManager().getGameData().getTotalCash() > 0);
	}
	
	@Test
	public void TestDeck() {
		assertNotNull(logic.getDeck());
		assertEquals(52, logic.getDeck().getDeckSize());
	}
	
	@Test
	public void getRandomIndexTest() {
		assertNotNull(logic.getRandomIndex());
		int randomIndex = logic.getRandomIndex();
		assertTrue(randomIndex >= 0 && randomIndex <= 51);

	}
	
	@Test
	public void getDealerCardsTest() {
		assertNotNull(logic.getDealerCards());

	}

	@Test
	public void getPlayerCardsTest() {
		assertNotNull(logic.getPlayerCards());

	}
	
	@Test
	public void TestCheckPlayerValue() {
		logic.checkPlayerValue(11);
		assertEquals(10, logic.getPtxt());
		logic.checkPlayerValue(5);
		assertEquals(5, logic.getPtxt()-10);
		assertThrows(IllegalArgumentException.class, () -> {logic.checkPlayerValue(-2);});
	}
	
	@Test
	public void TestCheckDealerValue() {
		logic.checkDealerValue(13);
		assertEquals(10, logic.getDtxt());
		logic.checkDealerValue(8);
		assertEquals(8, logic.getDtxt()-10);
		assertThrows(IllegalArgumentException.class, () -> {logic.checkDealerValue(-2);});
	}
	
	// TestCheckPlayerValue() og TestCheckDealerValue() tester for Play(), Hit() og Stay()-metodene 
	// I Logic-classen
	

	

}
