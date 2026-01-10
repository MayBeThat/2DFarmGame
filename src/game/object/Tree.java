package game.object;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import game.main.GamePanel;

public class Tree extends GameObject {
	
	public TreeType type;
	public TreeStage stage;
	public int col, row;
	public BufferedImage image;
	
	public Tree(TreeType type, int col, int row) {
		this.type = type;
		this.stage = TreeStage.SMALL;
		this.col = col;
		this.row = row;
		loadImage();
	}
	private void loadImage() {
		try {
			String path = "/objects/Tree/" + type.name().toLowerCase()+"_"+".png";
			image = ImageIO.read(getClass().getResourceAsStream(path));
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	public void draw(Graphics2D g2, GamePanel gp) {
		int x = col* gp.tileSize;
		int y = row* gp.tileSize;
		g2.drawImage(image, x, y, gp.tileSize,gp.tileSize,null);
	}

}
