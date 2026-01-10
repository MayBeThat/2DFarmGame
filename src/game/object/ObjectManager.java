package game.object;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;

import game.entity.Player;
import game.main.GamePanel;

public class ObjectManager {

    private final GamePanel gp;
    private final ArrayList<WorldObject> objects = new ArrayList<>();

    public ObjectManager(GamePanel gp) {
        this.gp = gp;
    }

    public void add(WorldObject obj) {
        if (obj != null)
            objects.add(obj);
    }

    public void draw(Graphics2D g2) {
        for (WorldObject obj : objects) {
            obj.draw(g2, gp);
        }
    }

    // KIỂM TRA PLAYER ĐANG ĐỨNG TRƯỚC OBJECT
    private boolean isPlayerFacingObject(Player p, WorldObject o) {
        int px = p.worldX;
        int py = p.worldY;

        Rectangle objRect = new Rectangle(
                o.worldX, o.worldY,
                o.width, o.height
        );

        Rectangle playerRect = new Rectangle(
                px + p.solidArea.x,
                py + p.solidArea.y,
                p.solidArea.width,
                p.solidArea.height
        );

        return playerRect.intersects(objRect);
    }
}

