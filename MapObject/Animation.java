package MapObject;

import java.awt.image.*;

public class Animation {
	
	public BufferedImage[] frames;
	int currentFrame;
	
	long startTime;
	long delay;

	public boolean playedOnce;
	
	public Animation() {
		
		playedOnce = false;
		
	}
	
	public void setDelay(long d) { delay = d; }
	public void setCurrentFrame(int i) { currentFrame = i; }
	public BufferedImage getImage(){ return frames[currentFrame]; } 
	public BufferedImage[] getFrame(){ return frames; }
	public void setFrame(BufferedImage[] frame) {
		this.frames = frame;
		currentFrame = 0;
		startTime = System.nanoTime();
		playedOnce = false;
	}
	
	public void update() {
		
		long elapsed = (System.nanoTime() - startTime) / 1000000;
		if(elapsed > delay) {
			currentFrame++;
			startTime = System.nanoTime();
		} 
		if(frames == null) System.out.println(1111);
		if(currentFrame == frames.length) {
			currentFrame = 0;
			playedOnce = true;
		}
	}
}
