package game.object;

import javax.imageio.ImageIO;

import game.main.GamePanel;

public class House extends GameObject {
		public House(GamePanel gp,int col, int row) {
			worldX = col * gp.tileSize;
			worldY = row * gp.tileSize;
			
			width = gp.tileSize *8 ;
			height = gp.tileSize *9;
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/objects/House/House.png"));
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}
