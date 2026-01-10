package game.tile;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Random;

import javax.imageio.ImageIO;

import game.main.GamePanel;

public class TileManager {
	//DIRT AND GRASS DEFAULT
	public static final int GRASS = 0;
	public static final int DIRT_PLANT = 12;

    GamePanel gp;
    public Tile[] tile;
    public int mapTileNum[][];
    int flowerTileNum[][];
    public TileManager(GamePanel gp) {
        this.gp = gp;
        
        tile = new Tile[20];
        mapTileNum = new int[gp.maxWorldCol][gp.maxWorldRow];
        flowerTileNum = new int[gp.maxWorldCol][gp.maxWorldRow];
        getTileImage();
        loadMap("/maps/map01.txt");
        generateFlowers();
    } 
    public void getTileImage() {
    	try {
        //GRASS
    	tile[0] = new Tile();
    	tile[0].image = ImageIO.read(getClass().getResourceAsStream("/tileMaps/grass_1.png"));
    	// Dirt_PLANT
    	tile[1] = new Tile();
    	tile[1].image = ImageIO.read(getClass().getResourceAsStream("/tileMaps/dirt_plant_1.png"));
    	//HOUSE
    	tile[2] = new Tile();
    	//SHOP
    	tile[3] = new Tile();
    	// DIRT
    	for(int i = 5; i<=7;i++) {
    		tile[i] = new Tile();
    		tile[i].image = ImageIO.read(getClass().getResourceAsStream("/tileMaps/Dirt_"+i+".png"));
    	}
    	//FLOWER
    	for(int i = 8; i <=11;i++) {
    		tile[i] = new Tile();
    		tile[i].image = ImageIO.read(getClass().getResourceAsStream("/tileMaps/flower_"+(i-7)+".png"));
    	}
    
    	    
    }catch(IOException e) {
    	e.printStackTrace();
    	}
    }
    public void loadMap(String path) {
        try {
            InputStream is = getClass().getResourceAsStream(path);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            
            
            int col = 0;
        	int row =0;
        	
        	while(col < gp.maxWorldCol && row < gp.maxWorldRow) {
        		String line = br.readLine();
        		
        		while(col < gp.maxWorldCol) {
        			String numbers[] = line.split(" ");
        			int num =Integer.parseInt(numbers[col]);
        			mapTileNum[col][row] = num;
        			col++;
                }
        		if(col == gp.maxWorldCol) {
        			col = 0;
        			row++;
        		}
        	}
            br.close();
        } catch(Exception e) {
            e.printStackTrace(); 
        }
    }
    public void generateFlowers() {
        Random rand = new Random();
        for(int col = 0; col < gp.maxWorldCol; col++) {
            for(int row = 0; row < gp.maxWorldRow; row++) {
            	//ONLY ON GRASS

                if(mapTileNum[col][row] == 0) { 

                    // 25% HAS FLOWERS
                    if(rand.nextInt(100) < 25) {

                        // RANDOM 1 to 4
                        flowerTileNum[col][row] = 8 + rand.nextInt(4);

                    } else {
                        flowerTileNum[col][row] = -1;
                    }

                } else {
                    flowerTileNum[col][row] = -1;
                }
            }
        }
    }

    public void draw(Graphics2D g2) {

        for(int row = 0; row < gp.maxWorldRow; row++) {
            for(int col = 0; col < gp.maxWorldCol; col++) {

                int tileNum = mapTileNum[col][row];

                int worldX = col * gp.tileSize;
                int worldY = row * gp.tileSize;

                int screenX = worldX - gp.player.worldX + gp.player.screenX;
                int screenY = worldY - gp.player.worldY + gp.player.screenY;

                // GROUND
                g2.drawImage(
                    tile[mapTileNum[col][row]].image, screenX, screenY,gp.tileSize, gp.tileSize,null);

                // FLOWER
                int flowerNum = flowerTileNum[col][row];
                if(flowerNum != -1) {
                    g2.drawImage( tile[flowerNum].image,screenX, screenY,gp.tileSize, gp.tileSize,null);
                    
                }
            }
        }	
        
    }

   
    }
