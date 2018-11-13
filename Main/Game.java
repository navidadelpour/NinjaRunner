package Main;

import java.io.File;

import javax.swing.JFrame;


public class Game {
	
	public File file;
	
	public static void main(String[] args) {
		
		JFrame frame = new JFrame("ninja runner");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(true);
		frame.setContentPane(new GamePanel());
		frame.setVisible(true);
		frame.pack();
		
	}
}
