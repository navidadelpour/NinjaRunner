package TileMap;

import java.awt.Graphics2D;
import java.awt.image.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

public class TileMap {

	private int rowNum;
	private int colNum;
	
	private int[][] map;
	private BufferedImage[][] tile;
	private int tileSize;
	private BufferedImage tileSet;
	private int[] playerPosition;
	private int[] doorPosition;
	private int[][] enemyPosition;
	private int[][] coinPosition;
	
	public TileMap(String map) {
		
		tileSize = 50;
		
		loadMap(map);
		loadTiles();
	}
	
	public void loadMap(String s) {
		try {
			
			BufferedReader reader = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(s)));
			
//			while(reader.ready()) {
//				System.out.println(reader.readLine());
			
			int numRow = Integer.parseInt(reader.readLine());
			int numCol = Integer.parseInt(reader.readLine());;
			map = new int[numRow][numCol];
			
			playerPosition = new int[2];
			String[] pp = reader.readLine().split("\\s");
			playerPosition[1] = Integer.parseInt(pp[0]);
			playerPosition[0] = Integer.parseInt(pp[1]);
			
			doorPosition = new int[2];
			String[] dp = reader.readLine().split("\\s");
			doorPosition[1] = Integer.parseInt(dp[0]);
			doorPosition[0] = Integer.parseInt(dp[1]);
			
			String[] ep = reader.readLine().split("\\s");
			enemyPosition = new int[ep.length / 2][2];
			for(int i = 0; i < ep.length; i += 2)  {
				enemyPosition[i / 2][0] = Integer.parseInt(ep[i + 1]);
				enemyPosition[i / 2][1] = Integer.parseInt(ep[i]);
			}

			String[] cp = reader.readLine().split("\\s");
			coinPosition = new int[cp.length / 2][2];
			for(int i = 0; i < cp.length; i += 2)  {
				coinPosition[i / 2][0] = Integer.parseInt(cp[i + 1]);
				coinPosition[i / 2][1] = Integer.parseInt(cp[i]);
			}
			
			for(int i = 0; i < numRow; i++) {
				String[] line = reader.readLine().split("\\s");
				for (int j = 0; j < numCol; j++) {
					map[i][j] = Integer.parseInt(line[j]);
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void loadTiles() {
		
		try {
			int tileSize2 = 30;
			tileSet = ImageIO.read(getClass().getResourceAsStream("/Resources/backgrounds/block.png"));
			
			rowNum = tileSet.getHeight() / tileSize2;
			colNum = tileSet.getWidth() / tileSize2;

			tile = new BufferedImage[rowNum][colNum];
			
			for(int r = 0; r < rowNum; r++) {
				for(int c = 0; c < colNum; c++) {

					tile[r][c] = tileSet.getSubimage(c * tileSize2, r * tileSize2, tileSize2, tileSize2);
					
				}
			}
			
		} catch (Exception e ) {
			e.printStackTrace();
		}
		
		
	}
	
	public void draw(Graphics2D g) {
		
		for(int r = 0; r < map.length; r++) {
			
			for(int c = 0; c < map[r].length; c++) {
				
				int row = map[r][c] / 10;
				int col = map[r][c] % 10;
				
				g.drawImage(tile[row][col], c * tileSize, r * tileSize, tileSize, tileSize, null);
				
//				g.setColor(Color.red);
//				g.drawRect(c * tileSize, r * tileSize, tileSize, tileSize);
//				g.setColor(Color.white);
//				g.setFont(new Font("Arial", Font.PLAIN, 10));
//				g.drawString(String.format("(" + r + "," + c + ")"), c * tileSize, r * tileSize);
			}
		}
	}
	
	public int getTileSize() {
		return tileSize;
	}
	
	public int[][] getMap() {
		return map;
	}
	
	public int[] getPlayerPosition() {
		return playerPosition;
	}
	
	public int[] getDoorPosition() {
		return doorPosition;
	}
	
	public int[][] getEnemyPosition() {
		return enemyPosition;
	}
	
	public int[][] getCoinPosition() {
		return coinPosition;
	}
}
