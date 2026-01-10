package game.object;

import java.awt.Rectangle;

import javax.imageio.ImageIO;

import game.entity.Player;
import game.main.GamePanel;

public class Well extends GameObject {
	//CONSTRUCTOR
	public Well(GamePanel gp,int col, int row) {
		worldX = col * gp.tileSize;
		worldY = row * gp.tileSize;
		
		width = gp.tileSize *6;
		height = gp.tileSize *5;
	try {
		image = ImageIO.read(getClass().getResourceAsStream("/objects/Well/Well_2.png"));
		
	}catch(Exception e) {
		e.printStackTrace();
	}

}}
