package GameState;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import java.awt.*;
import Main.GamePanel;

public class MainMenu extends GameState {

	BufferedImage bg;
	BufferedImage ninja;
	
	int x;
	int y;
	int dx;
	int dy;
	
	GameManeger gameManeger;
	
	String[] options;
	int currentChoice;
	
	String title;
	Font titleFont;
	Color titleColor;
	
	Font font;
	
	public MainMenu(GameManeger gameManeger) {
		
		try {
			
			bg = ImageIO.read(getClass().getResourceAsStream("/Resources/backgrounds/mainMenu.jpg"));
			ninja = ImageIO.read(getClass().getResourceAsStream("/Resources/backgrounds/ninja.png"));
			this.gameManeger = gameManeger;
			
			options = new String[]{"Start", "Help", "Quit" };
			currentChoice = 0;
			
			title = "ninja runner";
			titleFont = new Font("Arial", Font.BOLD, 56);
			titleColor = Color.BLACK;
			font = new Font("Times new romen", Font.PLAIN, 30);
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	@Override
	public void init() {
		
	}

	@Override
	public void update() {
		
	}
	
	@Override
	public void draw(Graphics2D g) {
		g.drawImage(bg, x, y, GamePanel.WIDTH, GamePanel.HEIGHT, null);
		g.drawImage(ninja, 50, 100, 200, 200,null);
		
		g.setFont(titleFont);
		g.setColor(titleColor);
		g.drawString(title, (GamePanel.WIDTH - 300) / 2, 200);
		
		g.setFont(font);
		for(int i = 0; i < options.length; i++) {
			if(i == currentChoice)
				g.setColor(Color.ORANGE);
			else 
				g.setColor(Color.white);
			g.drawString(options[i], GamePanel.WIDTH - 430, i * 50 + 300);
		}
	}
	
	@Override
	public void keyPressed(int k) {
		if(k == KeyEvent.VK_UP) {
			currentChoice--;
			if(currentChoice < 0) {
				currentChoice = options.length - 1;
			}
		}
		
		if(k == KeyEvent.VK_DOWN) {
			currentChoice++;
			if(currentChoice > options.length - 1) 
				currentChoice = 0;
		}
		if(k == KeyEvent.VK_ENTER) {
			switch (currentChoice) {
			case 0:
				gameManeger.nextLevel();
				break;
			case 1:
				
				break;
			case 2:
				System.exit(0);
				break;
			}
		}
	}
	
	@Override
	public void keyReleased(int k) {
		
	}
	
}
