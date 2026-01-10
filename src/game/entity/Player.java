package game.entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import game.crop.Crop;
import game.crop.CropType;
import game.item.SeedInventory;
import game.item.SeedType;
import game.main.GamePanel;
import game.main.KeyHandler;
import game.object.GameObject;
import game.tile.SoilState;

public class Player extends Entity {
    GamePanel gp;
    KeyHandler keyH;
    
    public final int screenX;
    public final int screenY;
    public int directionX = 0, directionY = 1;
    public SeedType currentSeed = SeedType.CARROT;
    public SeedInventory seedInv = new SeedInventory();

    private BufferedImage idleUp, idleDown, idleLeft, idleRight;
    private BufferedImage[] walkUp;
    private BufferedImage[] walkDown;
    private BufferedImage[] walkLeft;
    private BufferedImage[] walkRight;

    private int animIndex = 0;
    private int animCounter = 0;
    private int animSpeed = 6;
    private boolean moving = false;

    private String prevDirection = "down"; // để reset animation khi đổi hướng

    public Player(GamePanel gp, KeyHandler keyH) {
        this.gp = gp;
        this.keyH = keyH;
        
        screenX = gp.screenWidth / 2 - (gp.tileSize / 2);
        screenY = gp.screenHeight / 2 - gp.tileSize; 

        solidArea = new Rectangle(10,40,12,8);
        Rectangle interactArea = new Rectangle(0,0,32,32);
        
        setDefaultValues();
        loadImages();
    }

    private void setDefaultValues() {
        worldX = gp.tileSize * 15;
        worldY = gp.tileSize * 11;
        speed = 2;
        direction = "down";
    }
    public Rectangle getInteractArea() {
    	Rectangle area = new Rectangle(worldX,worldY,32,32);
    	switch(direction) {
    	case"up": area.y -=32;break;
    	case"down":area.y +=32;break;
    	case"left": area.x -=32;break;
    	case"right": area.x +=32;break;
    	}
    	return area;
    }

    private void loadImages() {
        try {
            idleUp    = ImageIO.read(getClass().getResourceAsStream("/Player/up_0.png"));
            idleDown  = ImageIO.read(getClass().getResourceAsStream("/Player/stand.png"));
            idleLeft  = ImageIO.read(getClass().getResourceAsStream("/Player/left_0.png"));
            idleRight = ImageIO.read(getClass().getResourceAsStream("/Player/right_0.png"));

            walkUp    = loadFrames("/player/up_", 6);
            walkDown  = loadFrames("/player/down_", 6);
            walkLeft  = loadFrames("/player/left_", 4);
            walkRight = loadFrames("/player/right_", 4);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private BufferedImage[] loadFrames(String prefix, int count) {
        BufferedImage[] frames = new BufferedImage[count];

        for (int i = 0; i < count; i++) {
            try {
                frames[i] = ImageIO.read(
                        getClass().getResourceAsStream(prefix + (i + 1) + ".png")
                );
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return frames;
    }

    public void update() {
        moving = false;
        

        // cập nhật hướng và di chuyển
        if (keyH.upPressed) {
            direction = "up";
            directionX = 0;
            directionY = -1;
            worldY -= speed;
            moving = true;
        }
        if (keyH.downPressed) {
            direction = "down";
            directionX = 0;
            directionY = 1;
            worldY += speed;
            moving = true;
        }
        if (keyH.leftPressed) {
            direction = "left";
            directionX = -1;
            directionY = 0;
            worldX -= speed;
            moving = true;
        }
        if (keyH.rightPressed) {
            direction = "right";
            directionX = 1;
            directionY = 0;
            worldX += speed;
            moving = true;
        }

        // reset animation nếu đổi hướng
        if (!direction.equals(prevDirection) || !moving) {
            animIndex = 0;
            animCounter = 0;
            prevDirection = direction;
        } else {
            // Update animation khi đang di chuyển
            animCounter++;
            if (animCounter >= animSpeed) {
                animIndex = (animIndex + 1) % getCurrentFrames().length;
                animCounter = 0;
            }
        }
        collisionOn = false;
        gp.cChecker.checkTile(this);

        if (keyH.spacePressed) {
            plantSeed();
            keyH.spacePressed = false;
        }
        if(gp.mouseH.leftPressed) {
//        	interactWithObject();
        }

        currentSeed = keyH.selectedSeed;
    }

    private void plantSeed() {
        int footX = worldX + solidArea.x + solidArea.width / 2;
        int footY = worldY + solidArea.y + solidArea.height;

        int col = footX / gp.tileSize;
        int row = footY / gp.tileSize;

        if (!canPlant(col, row)) return;
        
        CropType type = currentSeed.cropType;
        int daysToSt2 = type.daysToSt2;
        int daysToSt3 = type.daysToSt3;
        int reWaterSt2, reWaterSt3, reFer;
        switch(currentSeed){
        case CARROT:
        	reWaterSt2 = 1;
        	reWaterSt3 = 1;
        	reFer      = 1;
        	break;
        case TOMATO:
        	reWaterSt2 = 2;
        	reWaterSt3 = 3;
        	reFer      = 2;
        	break;
        case RICE:
        	reWaterSt2 = 4;
        	reWaterSt3 = 5;
        	reFer       = 3;
        	break;
        case SUNFLOWER:
        	reWaterSt2 = 2;
        	reWaterSt3 = 1;
        	reFer      = 2;
        	break;
        default: 
        	reWaterSt2 =1;
        	reWaterSt3 = 1;
        	reFer = 0;
        }
        Crop crop = new Crop(type,col,row,gp.gameTime.day,daysToSt2,reWaterSt2,daysToSt3,reWaterSt3,reFer);
        gp.cropM.plant(crop);
        seedInv.useSeed(currentSeed);
    }

    private boolean canPlant(int col, int row) {
        if (col < 0 || row < 0 || col >= gp.maxWorldCol || row >= gp.maxWorldRow)
            return false;
        if (gp.soilState[col][row] != SoilState.EMPTY)
            return false;
        if (!seedInv.hasSeed(currentSeed))
            return false;
        if (gp.cropM.hasCrop(col, row))
            return false;

        return true;
    }
    private BufferedImage[] getCurrentFrames() {
        switch (direction) {
            case "up":    return walkUp;
            case "down":  return walkDown;
            case "left":  return walkLeft;
            case "right": return walkRight;
        }
        return walkDown;
    }
   
    private BufferedImage getCurrentImage() {
        if (!moving) {
            switch (direction) {
                case "up":    return idleUp;
                case "down":  return idleDown;
                case "left":  return idleLeft;
                case "right": return idleRight;
            }
        }
        return getCurrentFrames()[animIndex];
    }

    public void draw(Graphics2D g2) {
        BufferedImage image = getCurrentImage();
        g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize * 2, null);
    }
}
