package GameState;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import Main.GamePanel;
import MapObject.Coin;
import MapObject.Door;
import MapObject.Enemy;
import MapObject.Player;
import TileMap.TileMap;

public class Levels extends GameState{
	
	int levelNum;
	GameManeger gameManeger;
	
	BufferedImage bg;
	BufferedImage tileMapBg;
	TileMap tileMap;
	int tileSize;
	
	Player player;
	int[] playerPosition;
	
	Enemy[] enemy;
	int[][] enemyPosition;
	
	Coin[] coin;
	int[][] coinPosition;
	
	Door door;
	int[] doorPosition;
	
	public Levels(int levelNum, GameManeger gameManeger) {
		
		try {
			this.levelNum = levelNum;
			bg = ImageIO.read(getClass().getResourceAsStream("/Resources/backgrounds/levels.jpg"));
			
			tileMapBg = new BufferedImage(GamePanel.WIDTH, GamePanel.HEIGHT, BufferedImage.TYPE_INT_RGB);
		
			this.gameManeger = gameManeger;
			
			tileMap = new TileMap("/Resources/levelsmap/level" + levelNum + ".txt");
			tileSize = tileMap.getTileSize();
			
			player = new Player(tileMap);
			playerPosition = tileMap.getPlayerPosition();
			player.setFirstPosition(playerPosition);
			player.setNewPosition(playerPosition[0] * tileSize + 10, playerPosition[1] * tileSize + 10);
			
			door = new Door(tileMap, player);
			doorPosition = tileMap.getDoorPosition();
			door.setNewPosition(doorPosition[0] * tileSize + 10, doorPosition[1] * tileSize);
			
			enemyPosition = tileMap.getEnemyPosition();
			enemy = new Enemy[enemyPosition.length];
			
			coinPosition = tileMap.getCoinPosition();
			coin = new Coin[coinPosition.length];
			
			for(int i = 0; i < enemyPosition.length; i++) {
				enemy[i] = new Enemy(tileMap, player);
				enemy[i].setNewPosition(enemyPosition[i][0] * tileSize + 10, enemyPosition[i][1] * tileSize + 10);
			}
			
			for(int i = 0; i < coinPosition.length; i++) {
				coin[i] = new Coin(tileMap, player);
				coin[i].setNewPosition(coinPosition[i][0] * tileSize + 15, coinPosition[i][1] * tileSize + 15);
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	@Override
	public void init() {
		
	}

	@Override
	public void update() {
		player.update();
		
		door.update();
		
		for(int i = 0; i < enemy.length; i++)
			enemy[i].update();
		
		for(int i = 0; i < coin.length; i++)
			coin[i].update();
		
		if(player.isDead()) {
			if(System.nanoTime() / 1000000000 - 2 == player.startDie)
				gameManeger.setCurrentLevel(0);
		}
		
		if(player.isWon()) {
			gameManeger.nextLevel();
		}

	}
	
	@Override
	public void draw(Graphics2D g) {
		g.drawImage(bg, 0, 0, GamePanel.WIDTH, GamePanel.HEIGHT, null);
		
		tileMap.draw(g);
		
		for(int i = 0; i < enemy.length; i++)
			enemy[i].draw(g);
		
		for(int i = 0; i < coin.length; i++){
			coin[i].draw(g);
		}
		
		door.draw(g);
		
		player.draw(g);
		
		g.setColor(Color.blue);
		g.drawString("level " + levelNum ,20, 20);
	}
	
	@Override
	public void keyPressed(int k) {
		switch(k) {
		
		case KeyEvent.VK_LEFT:
			player.setLeft(true);
		break;
		case KeyEvent.VK_RIGHT:
			player.setRight(true);
		break;
//		case KeyEvent.VK_UP:
//			player.setUp(true);
//		break;
//		case KeyEvent.VK_DOWN:
//			player.setDown(true);
//			break;
		case KeyEvent.VK_UP:
			player.setJumping();
			break;
		}
	}
	
	@Override
	public void keyReleased(int k) {
		
		switch(k) {
		
		case KeyEvent.VK_LEFT:
			player.setLeft(false);
		break;
		case KeyEvent.VK_RIGHT:
			player.setRight(false);
		break;
		case KeyEvent.VK_UP:
			player.setUp(false);
		break;
		case KeyEvent.VK_DOWN:
			player.setDown(false);
			break;
		
		}
	}
	
}