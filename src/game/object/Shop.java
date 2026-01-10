package game.object;

import javax.imageio.ImageIO;

import game.main.GamePanel;

public class Shop extends GameObject {
	public Shop(GamePanel gp,int col, int row) {
		worldX = col * gp.tileSize;
		worldY = row * gp.tileSize;
		
		width = gp.tileSize *6;
		height = gp.tileSize *4;
	try {
		image = ImageIO.read(getClass().getResourceAsStream("/objects/Shop/Shop.png"));
		
	}catch(Exception e) {
		e.printStackTrace();
	}
}}
