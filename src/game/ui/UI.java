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

    int col = (gp.player.worldX + gp.player.directionX * gp.tileSize) / gp.tileSize;
    int row = (gp.player.worldY + gp.player.directionY * gp.tileSize) / gp.tileSize;

    if(col < 0 || row < 0 || col >= gp.maxWorldCol || row >= gp.maxWorldRow) return;

    if(gp.tileM.mapTileNum[col][row] != TileManager.DIRT_PLANT) return;

    SoilState state = gp.soilState[col][row];

    String text = "Soil: ";

    switch(state) {
        case EMPTY -> text += "EMPTY";
        case READY -> text += "READY";
    }

    g2.setColor(Color.yellow);
    g2.drawString(text, 20, 220);
}


}
