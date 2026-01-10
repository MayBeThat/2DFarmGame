package game.object;

import javax.imageio.ImageIO;

import game.main.GamePanel;

public class FigureHead extends GameObject {
	//CONSTRUCTOR
	public FigureHead(GamePanel gp,int col, int row) {
		worldX = col * gp.tileSize;
		worldY = row * gp.tileSize;
		
		width = gp.tileSize *2;
		height = gp.tileSize *3;
	try {
		image = ImageIO.read(getClass().getResourceAsStream("/objects/Figurehead/figurehead.png"));
		
	}catch(Exception e) {
		e.printStackTrace();
	}

}
}
