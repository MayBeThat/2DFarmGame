package game.crop;

import java.awt.image.BufferedImage;

public enum CropType {
    CARROT(3, 3, 1, 1),
    TOMATO(2, 3, 3, 5),
    RICE(6, 3, 5, 2),
    SUNFLOWER(5, 3, 1, 1);

    public final int daysToSt2;
    public final int daysToSt3;
    public final int harvestAmount;
    public final int SeedReturn;
    private BufferedImage[] images; 

    CropType(int daysToSt2,int daysToSt3, int harvestAmount,int SeedReturn) {
        this.daysToSt2 = daysToSt2;
        this.daysToSt3 = daysToSt3;
        this.harvestAmount = harvestAmount;
        this.SeedReturn = SeedReturn;
    }

    public void setImages(BufferedImage[] imgs) {
        this.images = imgs;
    }

    public BufferedImage[] getImages() {
        return images;
    }

    public int getStageForDay(int daysPassed) {
        if (daysPassed <= 0) return 0; 
        int totalDays = daysToSt2 + daysToSt3;
        int stage = (daysPassed *3/ totalDays);
        return Math.min(stage,2);
    }
}