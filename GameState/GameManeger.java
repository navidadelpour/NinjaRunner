package GameState;

import java.awt.Graphics2D;

public class GameManeger {

	private int mapNum;
	private GameState[] gameState;
	private int currentLevel;
	
	
	public GameManeger() {
		
//		mapNum = new File(getClass().getResource("/levelsmap").getPath()).listFiles().length;
		mapNum = 3;
		
		gameState = new GameState[mapNum + 1];
	
		currentLevel = 0;
		gameState[currentLevel] = new MainMenu(this);
		
		
	}
	
	public void init() {
		if(gameState[currentLevel] != null)
			gameState[currentLevel].init();
	}
	
	public void update() {
		if(gameState[currentLevel] != null)
			gameState[currentLevel].update();
	}
	
	public void draw(Graphics2D g) {
		if(gameState[currentLevel] != null)
			gameState[currentLevel].draw(g);
	}
	
	public void keyReleased(int k) {
		if(gameState[currentLevel] != null)
			gameState[currentLevel].keyReleased(k);
	}
	
	public void keyPressed(int k) {
		if(gameState[currentLevel] != null)
			gameState[currentLevel].keyPressed(k);
	}
	
	public int getCurrentLevel() {
		return currentLevel;
	}
	
	public void setCurrentLevel(int c) {
		currentLevel = c;
	}
	
	public void nextLevel() {
		if(currentLevel < gameState.length - 1){
			currentLevel++;
			gameState[currentLevel] = new Levels(currentLevel, this);
		}
		else{
			currentLevel = 0;
			gameState[currentLevel] = new MainMenu(this);
		}
	}
	
	
}
