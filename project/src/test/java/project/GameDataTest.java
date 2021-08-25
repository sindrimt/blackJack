package project;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GameDataTest {

	private GameData gamedata;
	private int cash = 40;
	private int bet = 20;
	
	@BeforeEach
	public void setup() {
		gamedata = new GameData();
	}
	
	@Test
	public void setTotalCash() {
		gamedata.setTotalCash(cash);
		assertEquals(cash, gamedata.getTotalCash());
	}
	
	@Test
	public void setCurrentBet() {
		gamedata.setCurrentBet(bet);
		assertEquals(bet, gamedata.getCurrentBet());
	}

	
}
