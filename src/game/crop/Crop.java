package game.crop;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import game.main.GamePanel;

public class Crop {
    public CropType type;
    public int col, row;
    public int plantedDay;
    public int status = 1;
    public int wCountSt2 =0;
    public int wCountSt3 = 0;

    
    public int daysToSt2;
    public int daysToSt3;
    
    public int reWaterSt2; //n1
    public int reWaterSt3; //n2
    public BufferedImage[] images;

    private boolean wateredToday = false;
    private int lastDay = -1;
    private int lastWaterDay = -1;



    public Crop(CropType type, int col, int row,int plantedDay,int daysToSt2,
    		int reWaterSt2,int daysToSt3,int reWaterSt3) {
        this.type = type;
        this.col = col;
        this.row = row;
        this.plantedDay = plantedDay;
        this.daysToSt2 = daysToSt2;
        this.reWaterSt2 = reWaterSt2;
        this.daysToSt3 = daysToSt3;
        this.reWaterSt3 = reWaterSt3;
        this.images = type.getImages();
    }
    
    // CALL WHEN WATER
    public void water(int currentDay) {
        if (lastWaterDay == currentDay) return;
        lastWaterDay = currentDay;

        if (status == 1 ) wCountSt2++;
        else if (status == 2) wCountSt3++;

    }
    public boolean isWateredToday(int currentDay) {
        return lastWaterDay == currentDay;
    }
    public void update(int currentDay) {
        if (currentDay != lastDay) {
            wateredToday = false;
            lastDay = currentDay;
        }
        int daysPassed = currentDay - plantedDay;
        //1 -> 2
        if (status == 1) {
            if (daysPassed >= daysToSt2 && wCountSt2 >= reWaterSt2 && currentDay > lastWaterDay) {
                status = 2;
            }
        }
        //2 -> 3
        if (status == 2) {
            if (daysPassed >= daysToSt2 + daysToSt3 && wCountSt3 >= reWaterSt3 && currentDay > lastWaterDay) {
                status = 3;
            }
        }
        if (status < 1) status = 1;
        if (status > 3) status = 3;
    }
    public int getStatus() {
        return status;
    }
    public int getWCountSt2() { return wCountSt2; }
    public int getWCountSt3() { return wCountSt3; }
    public int getReWaterSt2() { return reWaterSt2; }
    public int getReWaterSt3() { return reWaterSt3; }

    public BufferedImage getCurrentImage() {
    	int idx = status - 1;
    	if(idx<0) idx =0;
    	if(idx>= images.length) idx = images.length -1;
    	return images[idx];
    }
    public boolean canHarvest() {
    	return status == 3;
    }
    public void draw(Graphics2D g2, GamePanel gp) {
        BufferedImage img = getCurrentImage();
        if (img == null) return;

        int worldX = col * gp.tileSize;
        int worldY = row * gp.tileSize;

        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;

        g2.drawImage(img, screenX, screenY, gp.tileSize, gp.tileSize, null);
    }




}