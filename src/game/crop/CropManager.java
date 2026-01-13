package game.crop;

import java.awt.Graphics2D;
import java.util.ArrayList;

import game.item.ProduceType;
import game.item.SeedType;
import game.main.GamePanel;

public class CropManager {
    private final GamePanel gp;
    private final ArrayList<Crop> crops = new ArrayList<>();

    public CropManager(GamePanel gp) {
        this.gp = gp;
    }

    public void plant(Crop crop) {
        if (crop != null)
            crops.add(crop);
        gp.soilState[crop.col][crop.row] = game.tile.SoilState.PLANTED;
    }

    public boolean hasCrop(int col, int row) {
        for (Crop c : crops) {
            if (c.col == col && c.row == row)
                return true;
        }
        return false;
    }

    public Crop getCropAt(int col, int row) {
        for (Crop c : crops) {
            if (c.col == col && c.row == row) return c;
        }
        return null;
    }

    public void update() {
        int day = gp.gameTime.day;
        for (Crop c : crops) {
            if (c != null) c.update(day);
        }
    }

    public void draw(Graphics2D g2) {
        for (Crop c : crops) {
            if (c != null) {
                c.draw(g2, gp);
            }
        }
    }

    // WATERING
    public void waterCrop(int col, int row) {
        for (Crop c : crops) {
            if (c.col == col && c.row == row) {
                c.water(gp.gameTime.day);
                break;
            }
        }
    }

    // HARVEST
    public void harvestCrop(int col, int row) {
        Crop target = null;
        for (Crop c : crops) {
            if (c.col == col && c.row == row) {
                target = c;
                break;
            }
        }
        if (target != null && target.canHarvest()) {
            crops.remove(target);
            gp.soilState[col][row] = game.tile.SoilState.EMPTY;
            gp.player.produceInv.addProduce(ProduceType.valueOf(target.type.name()), target.type.harvestAmount);
            gp.player.seedInv.addSeed(SeedType.valueOf(target.type.name()), target.type.SeedReturn);
        }
    }
    public void debugPrint() {
        int day = gp.gameTime.day;
        for (Crop c : crops) {
            System.out.println(
                    c.type + " | dayPassed=" + (day - c.plantedDay)
                            + " | status=" + c.status
                            + " | w2=" + c.wCountSt2 + "/" + c.reWaterSt2
                            + " | w3=" + c.wCountSt3 + "/" + c.reWaterSt3
            );
        }
    }

}
