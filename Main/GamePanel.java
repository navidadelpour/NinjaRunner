package Main;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import GameState.GameManeger;
import javax.swing.JPanel;


@SuppressWarnings("serial")
public class GamePanel extends JPanel implements Runnable, KeyListener {
	
	public static final int WIDTH = 800;
	public static final int HEIGHT = 600;
	
	int FPS = 60;
	long targetTime = 1000 / FPS;
	
	
	BufferedImage bg;
	Graphics2D g;
	
	Thread thread;
	boolean running;
	
	GameManeger maneger;
	
	public GamePanel() {
		super();
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		setFocusable(true);
		requestFocus();
		
		bg = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		g = bg.createGraphics();
		
		thread = new Thread(this);
		addKeyListener(this);
		running = true;
		thread.start();
	}
	
	
	@Override
	public void run() {
		
		init();
		
		long start;
		long elapsed;
		long wait;
		
		while(running) {
			
			start = System.nanoTime();
			
			update();
			draw();
			repaint();

			elapsed = System.nanoTime() - start;
			wait = targetTime - elapsed / 1000000;
			if(wait < 0) wait = 5;
			try {
				Thread.sleep(wait + 2);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}
	
	public void init() {
		maneger = new GameManeger();
		maneger.init();
	}
	
	public void update() {
		maneger.update();
	}
	
	public void draw() {
		maneger.draw(g);
	}
	
	@Override
	public void paintComponent(Graphics g2) {
		super.paintComponent(g2);
		g2.drawImage(bg, 0, 0, null);
	}
	
	@Override
	public void keyReleased(KeyEvent k) {
		maneger.keyReleased(k.getKeyCode());
	}	
	@Override
	public void keyPressed(KeyEvent k) {
		maneger.keyPressed(k.getKeyCode());
	}
	@Override
	public void keyTyped(KeyEvent k) {}
	
}