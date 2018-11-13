package MapObject;


import javax.imageio.ImageIO;

import TileMap.TileMap;

public class Enemy extends MapObject{

	private Player player;
	
	public Enemy(TileMap tileMap, Player player) {
		
		super(tileMap);
		
		this.player = player;
		
		width = 40;
		height = 40;
		
		moveSpeed = 0.2;
		stopSpeed = 0.3;
		maxSpeed = 1;
		jumpSpeed = 10;
		fallSpeed = .6;
		maxFallSpeed = 10;
		
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/Resources/mapobjects/enemy.png"));
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public void portal() {
		
		if(map[thisRow][thisCol + 1] > 0 || map[thisRow + 1][thisCol + 1] == 0) {
			setRight(false);
			setLeft(true);
		}
		if(map[thisRow][thisCol] > 0 || map[thisRow + 1][thisCol] == 0) {
			setLeft(false);
			setRight(true);
		}
	}
	
	public void update() {
		
		checkIntersects();
		portal();
		getNextPosition();
		checkCollision();
		setNewPosition(x, y);
	}
	
	public void checkIntersects() {
		
		if(this.box().intersects(player.box())){
			if(!dead){
				player.removeHealth();
				player.blink = true;
				x = 0;
				y = 0;
			}
			dead = true;
		}
	}
	
}
