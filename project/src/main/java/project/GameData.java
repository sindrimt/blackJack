package project;

public class GameData {

	private int totalCash;
	private int currentBet;
	
	public int getCurrentBet() {
		return currentBet;
	}
	
	public void setCurrentBet(int currentBet) {
		this.currentBet = currentBet;
	}
	public int getTotalCash() {
		return totalCash;
	}
	
	public void setTotalCash(int totalCash) {
		this.totalCash = totalCash;
	}
	
	public void addTotalCash(int value) {
		totalCash += value;
	}
}
