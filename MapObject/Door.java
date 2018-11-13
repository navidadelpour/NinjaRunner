package MapObject;

import javax.imageio.ImageIO;

import TileMap.TileMap;

public class Door extends MapObject{
 
	private Player player;
	
	public Door(TileMap tileMap, Player player) {
		
		super(tileMap);
		
		this.player = player;
		width = 30;
		height = tileSize;
		dead = false;
		
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/Resources/mapobjects/door.png"));
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void update() {
		checkIntersects();
	}
	
	public void checkIntersects() {
		if(this.box().intersects(player.box())) {
			player.win(true);
		}
	}
}
