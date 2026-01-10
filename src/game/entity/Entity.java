package game.entity;

import java.awt.Rectangle;

public abstract class Entity {

	public int worldX, worldY;
	public int speed;
	public String direction;
	public Rectangle solidArea;
    public int solidAreaDefaultX, solidAreaDefaultY;
    public boolean collisionOn = false;
	}

