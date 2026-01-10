package game.object;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import game.main.GamePanel;

public class WorldObject {
//POSITION IN WORLD
	public int worldX, worldY;
	
// SIZE
	public int width, height;
// COLLISION
	public Rectangle soilArea;
// IMAGE
	public BufferedImage image;
// CONSTRUCTOR
	public WorldObject() {}
	
// DRAW
	public void draw(Graphics2D g2, GamePanel gp) {
		int screenX = worldX - gp.player.worldX + gp.player.screenX;
		int screenY = worldY - gp.player.worldY + gp.player.screenY;
		
		g2.drawImage(image,screenX, screenY,width, height,null);
	}
}
