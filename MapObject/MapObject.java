package MapObject;

import java.awt.*;
import TileMap.TileMap;

import java.awt.image.BufferedImage;

public abstract class MapObject {

	protected TileMap tileMap;
	protected int[][] map;
	protected int tileSize;
	protected BufferedImage image;
	
	public int width;
	public int height;

	public double x;
	public double y;
	protected double dx;
	protected double dy;
	
	protected int thisCol; 
	protected int thisRow;
	
	protected int rightCorner;
	protected int leftCorner;
	protected int upCorner;
	protected int downCorner;

	
	protected double moveSpeed;
	protected double stopSpeed;
	protected double maxSpeed;
	protected double jumpSpeed;
	protected double fallSpeed;
	protected double maxFallSpeed;
	
	
	protected boolean right;
	protected boolean up;
	protected boolean left;
	protected boolean down;
	protected boolean facingRight;
	protected boolean jumping;
	protected boolean falling;
	protected boolean dead;
	
	protected Animation animation;
	
	
	public MapObject(TileMap tileMap) {
		
		this.tileMap = tileMap;
		this.tileSize = tileMap.getTileSize();
		this.map = tileMap.getMap();
		

	}
	
	
	public void setRight(boolean b) { right = b; }
	public void setUp (boolean b) { up = b; }
	public void setLeft (boolean b) { left = b; }
	public void setDown (boolean b) { down = b; }
	public void setJumping() { jumping = true; }
	
	public void checkCollision() {
		
		thisCol = (int) x / tileSize;
		thisRow = (int) y / tileSize;
		
		rightCorner = (int) x + width;
		leftCorner = (int) x;
		upCorner = (int) y;
		downCorner = (int) y + height;
		
		if(map[thisRow][thisCol + 1] > 0 && rightCorner > (thisCol + 1) * tileSize - 1.5){
			if(dx > 0) {
				dx = 0;
				x = (thisCol + 1) * tileSize - width;
			}
		}
		
		if(map[thisRow][thisCol] > 0 && leftCorner > thisCol * tileSize + 1){
			if(dx < 0) {
				dx = 0;
				x = (thisCol + 1) * tileSize + 2;
			}
		}
		
		if(map[thisRow][thisCol] > 0 && upCorner > thisRow * tileSize - 1.5){
			if(dy < 0) {
				dy = 0;
				y = (thisRow + 1) * tileSize + 1;
			}
		}
		
		if(map[thisRow][thisCol + 1] > 0) {
			if(rightCorner > (thisCol + 1) * tileSize + 1) {
				dy = 0;
				y = (thisRow + 1) * tileSize + 1;
			}
		}
		
		
		if((map[thisRow + 1][thisCol] > 0 && (downCorner >= (thisRow + 1) * tileSize)) ||
			map[thisRow + 1][thisCol + 1] > 0 && rightCorner > (thisCol + 1) * tileSize){
			if(dy > 0) {
				dy = 0;
				y = (thisRow + 1) * tileSize - height;
				falling = false;
			}
		} else {
			falling = true;
			
			if(map[thisRow + 1][thisCol + 1] > 0) {
				if(rightCorner < (thisCol + 1 ) * tileSize + 1){
					falling = true;
				} else {
					if(dy > 0) {
						dy = 0;
						y = (thisRow + 1) * tileSize - width + 1;
						falling = false;
					}
				}
			}
		}
	}
	
public void getNextPosition() {
		
		//left and right
		
		if(left) {
			dx -= moveSpeed;
			if(dx < -maxSpeed) {
				dx = -maxSpeed;
			}
		}
		
		else if(right) {
			dx += moveSpeed;
			if(dx > maxSpeed) {
				dx = maxSpeed;
			}
		}
		else {
			if(dx > 0) {
				dx -= stopSpeed;
				if(dx < 0) {
					dx = 0;
				}
			}
			else if(dx < 0) {
				dx += stopSpeed;
				if(dx > 0) {
					dx = 0;
				}
			}
		}
		
		//up and down
		
//		if(up) {
//			dy -= moveSpeed;
//			if(dy < -maxSpeed) {
//				dy = -maxSpeed;
//			}
//		}
//		
//		else if(down) {
//			dy += moveSpeed;
//			if(dy > maxSpeed) {
//				dy = maxSpeed;
//			}
//		}
//		else {
//			if(dy > 0) {
//				dy -= stopSpeed;
//				if(dy < 0) {
//					dy = 0;
//				}
//			}
//			else if(dy < 0) {
//				dy += stopSpeed;
//				if(dy > 0) {
//					dy = 0;
//				}
//			}
//		}
		
		//jumping and falling
		if (jumping && !falling) {
			dy -= jumpSpeed;
			falling = true;
		}
		
		if(falling) {
			dy += fallSpeed;
			if(dy > 0) jumping = false;
			if(dy > maxFallSpeed)
				dy = maxFallSpeed;
		}

		if(y > 530) {
			if(this.getClass().getName().equals("MapObject.Player")) {
				Player p = (Player) this;
				p.removeHealth();
				p.setNewPosition(p.getFirstPosition(0) * tileSize + 20, p.getFirstPosition(1) * tileSize - 10);
			} else {
				dead = true;
				dy = 0;
				falling = false;
				y = 0;
				x = 0;
			}
		}
		
	}
	
	public void draw(Graphics2D g) {
		if(!dead)
			g.drawImage(image, (int) x, (int) y, width, height, null);
	}

	public void setVector(double dx, double dy) {
		this.dx = dx;
		this.dy = dy;
	}
	
	public void setNewPosition(double x, double y) {
		this.x = x + dx;
		this.y = y + dy;
	}
	
	public Rectangle box() {
		return new Rectangle((int)x, (int)y, width, height);
	}
	
	public boolean isDead() {
		return this.dead;
	}
}
