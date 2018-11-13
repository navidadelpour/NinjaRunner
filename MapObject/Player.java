package MapObject;

import java.awt.Graphics2D;
import java.awt.*;

import javax.imageio.ImageIO;

import TileMap.TileMap;
import java.util.ArrayList;
import java.awt.image.*;

public class Player extends MapObject {
	
	private int health;
	private int maxHealth;
	private int coinNum;
	private boolean win;
	
	private int[] firstPosition;
	
	private int currentAction;
	private static final int IDLE = 0;
	private static final int WALK = 1;
	private static final int JUMP = 2;
	private static final int FALL = 3; 
	public static final int DEAD = 4;
	private final int[] numFrames = { 2, 5, 1, 1, 3 };

	private BufferedImage spritesheet;
	private ArrayList<BufferedImage[]> sprite;
	public long startDie;
	public boolean blink;
	
	
	public Player (TileMap tileMap) {
		
		super(tileMap);
		
		try {
		
			width = 20;
			height = 40;
		
			moveSpeed = 0.5;
			stopSpeed = 0.8;
			maxSpeed = 6;
			
			jumpSpeed = 15;
			fallSpeed = .8;
			maxFallSpeed = 8;
			
			maxHealth = 3;
			health = maxHealth;
			
			firstPosition = new int[2];
			blink = false;
			
//			image = ImageIO.read(getClass().getResourceAsStream("/mapobjects/1.jpg"));
			spritesheet = ImageIO.read(getClass().getResourceAsStream("/Resources/mapobjects/player.png"));
			sprite = new ArrayList<BufferedImage[]>();
			
			for(int r = 0; r < numFrames.length; r++) {
				
				BufferedImage[] bi = new BufferedImage[numFrames[r]];
				
				for(int c = 0; c < numFrames[r]; c++) {
					if(r != numFrames.length - 1)
						bi[c] = spritesheet.getSubimage(c * width, r * height, width, height);
					else
						bi[c] = spritesheet.getSubimage(c * width * 2, r * height, width * 2, height);
				}
				sprite.add(bi);
			}
			
			animation = new Animation();
			currentAction = IDLE;
			animation.setFrame(sprite.get(IDLE));
			animation.setDelay(400);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	public void update() {
		
		getNextPosition();
		checkCollision();
		setNewPosition(x, y);
		if(dead){
			setAnimation(DEAD, 100);
		}else if(dy < 0) 
			setAnimation(JUMP, 100);
		else if(dy > 0) 
			setAnimation(FALL, 100);
		else if(left || right) {
			setAnimation(WALK, 40);
			if(dx > 0) facingRight = true;
			if(dx < 0) facingRight = false;
		}
		else 
			setAnimation(IDLE, 400);
		
		animation.update();
		if(dead && animation.playedOnce){
			animation.currentFrame = sprite.get(DEAD).length - 1; 
			dx = 0;
			dy = 0;
		}
		
		
	}
	public void setAnimation(int i, long l) {
		if(currentAction != i) {
			currentAction = i;
			animation.setFrame(sprite.get(i));
			animation.setDelay(l);
			if(i == DEAD) {
				width = 60;
			}
		}
	}
	
	public void addCoin() {
		this.coinNum++;
	}
	
	public void removeHealth() {
		this.health--;
		if(health == 0){
			startDie = System.nanoTime() / 1000000000;
			dead = true;
		}
	}
	
	public void win(boolean b) {
		win = b;
	}
	
	public boolean isWon() {
		return win;
	}
	
	int cc = 0;
	@Override
	public void draw(Graphics2D g){
//		super.draw(g);
		g.setColor(Color.YELLOW);
		g.drawString("coins : " + coinNum, 20, 50);
		g.setColor(Color.RED);
		g.drawString("health : " + health + " / " + maxHealth, 20, 80);
		
		if(blink && (System.nanoTime() / 1000000) % 2 == 0 && !dead){
				cc++;
				if(cc == 50){
					blink = false;
					cc = 0;
				}
				return;
		}
		else{
			if(facingRight)
				g.drawImage(animation.getImage(), (int) x, (int) y, width, height, null);
			else 
				g.drawImage(animation.getImage(), (int)x + width, (int)y, -width, height, null);
		}

		
	}
	
	
	public double getFirstPosition(int x) {
		return firstPosition[x];
	}
	
	public void setHealth(int x) {
		health = x;
	}
	
	public int getHealth() {
		return health;
	}
	
	public void setFirstPosition(int[] fp) {
		this.firstPosition = fp;
	}
	
}
