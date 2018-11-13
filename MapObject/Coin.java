package MapObject;

import java.awt.Graphics2D;
import java.awt.image.*;

import javax.imageio.ImageIO;

import TileMap.TileMap;

public class Coin extends MapObject{

	private Player player;
	private BufferedImage[] sprite;
	
	
	public Coin(TileMap tileMap, Player player) {
		
		super(tileMap);
		
		this.player = player;
		width = 40;
		height = 40;
		
		try {
		
			image = ImageIO.read(getClass().getResource("/Resources/mapobjects/coin.png"));
			sprite = new BufferedImage[10];
			for(int r = 0; r < 10; r++) {
				sprite[r] = image.getSubimage(r * width, 0, width, height);
			}
			
			animation = new Animation();
			animation.setFrame(sprite);
			animation.setDelay(40);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void update() {
		checkIntersects();
		animation.update();
	}
	
	public void checkIntersects() {
		if(this.box().intersects(player.box())){
			if(!dead)
				player.addCoin();
			dead = true;
		}
	}
	
	@Override
	public void draw(Graphics2D g) {
		if(!dead)
			g.drawImage(animation.getImage(),(int) x,(int) y, width / 2, height / 2, null);
	}
}
