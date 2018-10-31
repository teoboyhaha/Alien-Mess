package Particles;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

import Entity.Entity;
import Entity.ID;
import Level.Level;
import Sprites.SpriteSheet;

public class Particle extends Entity {

	private Level level;
	private Color color;
	
	private int life;
	private int time = 0;
	
	protected double xx, yy, zz;
	protected double velX, velY, velZ;
	
	private Random random = new Random();
	
	public Particle(int x, int y, ID id, SpriteSheet sheet, Level level, int life, Color color) {
		super(x, y, id, sheet);
		this.x = x;
		this.y = y;
		this.level = level;
		this.color = color;
		this.xx = x;
		this.yy = y;
		this.life = life + (random.nextInt(20) - 10);
		
		this.velX = random.nextGaussian();
		this.velY = random.nextGaussian();
		this.zz = random.nextFloat() + 2.0;
	}

	@Override
	public void update() {
		
		time++;
		if(time >= 7400) { time = 0; }
		if(time > life) { level.remove(this); }
		
		velZ -= 0.1;
		
		if(zz < 0) {
			zz = 0;
			velZ *= -0.55;
			
			velX *= 0.4;
			velY *= 0.4;
		}
		
		move(xx + velX, (yy + velY) + (zz + velZ));
	}

	@Override
	public void render(Graphics g) {
		g.setColor(color);
		g.fillRect((int) xx - 1, (int) yy - (int) zz - 1, 8, 8);
	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle(x, y, 8, 8);
	}
	
	private void move(double x, double y) {	
		this.xx += velX;
		this.yy += velY;
		this.zz += velZ;
	}
}
