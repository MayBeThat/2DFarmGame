package game.object;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import game.entity.Player;
import game.main.GamePanel;

public abstract class GameObject {

	public int worldX, worldY;
	public BufferedImage image;
	public int width, height;
	
	public void draw(Graphics2D g2,GamePanel gp) {
		int screenX = worldX - gp.player.worldX + gp.player.screenX;
		int screenY = worldY - gp.player.worldY + gp.player.screenY;
		g2.drawImage(image,screenX, screenY,width, height,null);
	}
}
