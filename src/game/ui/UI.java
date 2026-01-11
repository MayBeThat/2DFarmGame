package game.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import game.item.SeedType;
import game.main.GamePanel;
import game.tile.SoilState;
import game.tile.TileManager;

public class UI {

    GamePanel gp;

    public UI(GamePanel gp) {
        this.gp = gp;
    }
    public void draw(Graphics2D g2) {

        drawTime(g2);
        drawSeedInfo(g2);
        drawSoilInfo(g2);
        drawProduceInventory(g2);
    }

    // DRAW TIME
private void drawTime(Graphics2D g2) {
	g2.setColor(Color.white);
	g2.setFont(new Font("Arial", Font.BOLD,12));

    String timeText = String.format(
        "Day %d  %02d:%02d",
        gp.gameTime.day,
        gp.gameTime.hour,
        gp.gameTime.minute
    );
    g2.drawString(timeText, 20, 40);
}
// DRAW PLANT
private void drawSeedInfo(Graphics2D g2) {

    int x = 20;
    int y = 60;

    SeedType current = gp.player.currentSeed;
    int amount = gp.player.seedInv.getAmount(current);

    g2.drawString("Seed: " + current.name(), x, y);
    g2.drawString("Amount: " + amount, x, y + 20);

    g2.drawString("[1] CARROT", x, y + 50);
    g2.drawString("[2] RICE", x, y + 70);
    g2.drawString("[3] SUNFLOWER", x, y + 90);
    g2.drawString("[4] TOMATO", x, y + 110);
   
    
}
// DRAW DIRT
private void drawSoilInfo(Graphics2D g2) {

    int footX = gp.player.worldX + gp.player.solidArea.x + gp.player.solidArea.width / 2;
    int footY = gp.player.worldY + gp.player.solidArea.y + gp.player.solidArea.height;

    int col = footX / gp.tileSize;
    int row = footY / gp.tileSize;
    if(col < 0 || row < 0 || col >= gp.maxWorldCol || row >= gp.maxWorldRow) return;

    int tileNum = gp.tileM.mapTileNum[col][row];
    if(tileNum !=1) return;

    int x = 20;
    int y = 220;

    SoilState state = gp.soilState[col][row];
    g2.setColor(Color.yellow);
    // Soil state
    g2.drawString("Soil: " + state.name(), x, y);
    y += 18;
    // Crop info
    var crop = gp.cropM.getCropAt(col, row);
    if(crop == null) {
        g2.drawString("Crop: NONE", x, y);
        return;
    }
    g2.drawString("Crop: " + crop.type.name() + " | Stage: " + crop.getStatus(), x, y);
    y += 18;
    // Watered today
    g2.setColor(crop.isWateredToday(gp.gameTime.day) ? Color.green : Color.red);
    g2.drawString(crop.isWateredToday(gp.gameTime.day) ? "Watered Today" : "Need Water", x, y);
}
    private void drawProduceInventory(Graphics2D g2) {
        // Vị trí bảng
        int startX = 140;
        int startY = 330;

        // Kích thước slot
        int slotSize = 36;
        int padding = 20;       // khoảng cách giữa slot
        int cols = 6;          // 6 ô một hàng

        // Danh sách item để vẽ theo thứ tự
        game.item.ProduceType[] items = game.item.ProduceType.values();

        // Vẽ từng slot
        for (int i = 0; i < items.length; i++) {
            int r = i / cols;
            int c = i % cols;

            int x = startX + c * (slotSize + padding);
            int y = startY + r * (slotSize + padding);
            // slot background
            g2.setColor(new Color(200, 200, 200, 220));
            g2.fillRoundRect(x, y, slotSize, slotSize, 8, 8);
            // slot border
            g2.setColor(new Color(80, 80, 80, 255));
            g2.drawRoundRect(x, y, slotSize, slotSize, 8, 8);
            // icon
            var type = items[i];
            var icon = type.getIcon();
            if (icon != null) {
                int iconSize = slotSize - 10;
                int ix = x + (slotSize - iconSize) / 2;
                int iy = y + (slotSize - iconSize) / 2;
                g2.drawImage(icon, ix, iy, iconSize, iconSize, null);
            }
            // amount
            int amount = gp.player.produceInv.getAmount(type);
            if (amount > 0) {
                String txt = String.valueOf(amount);
                // shadow
                g2.setColor(Color.black);
                g2.drawString(txt, x + slotSize - 10 - txt.length() * 6, y + slotSize - 6);
                // white number
                g2.setColor(Color.white);
                g2.drawString(txt, x + slotSize - 11 - txt.length() * 6, y + slotSize - 7);
            }
        }
    }



}
