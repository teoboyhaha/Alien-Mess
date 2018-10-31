package Entity;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import Sprites.SpriteSheet;

public abstract class Entity {

	protected int x;
	protected int y;
	protected float velX = 0, velY = 0;
	
	protected ID id;
	protected SpriteSheet sheet;
	protected BufferedImage sprite_sheet = null;
	
	public Entity(int x, int y, ID id, SpriteSheet sheet) {
		this.x = x;
		this.y = y;
		this.id = id;
		this.sheet = sheet;
	}
	
	public abstract void update();
	public abstract void render(Graphics g);
	public abstract Rectangle getBounds();
	
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public float getVelX() {
		return velX;
	}

	public void setVelX(float velX) {
		this.velX = velX;
	}

	public float getVelY() {
		return velY;
	}

	public void setVelY(float velY) {
		this.velY = velY;
	}

	public ID getId() {
		return id;
	}

	public void setId(ID id) {
		this.id = id;
	}
}
