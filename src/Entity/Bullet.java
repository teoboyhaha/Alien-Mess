package Entity;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import Level.Level;
import Main.Game;
import Sprites.SpriteSheet;

public class Bullet extends Entity {
	
	private Level level;
	private int time = 0;
	
	public Bullet(int x, int y, int velX, ID id, Level level, SpriteSheet sheet) {
		super(x, y, id, sheet);
		this.level = level;
		this.velX = velX;
	}

	@Override
	public void update() {
		
		time++;
		if(time >= 7) {
			time = 0;
		}
		
		x += velX;
		y += velY;
		
		for(int i = 0; i < level.entityList.size(); i++) {
			Entity entity = level.entityList.get(i);
			if(entity.getId() == ID.GrassTile) {
				if(getBounds().intersects(entity.getBounds())) {
					level.remove(this);
				}
			}
			if(entity.getId() == ID.Alien) {
				if(getBounds().intersects(entity.getBounds())) {
					if(time >= 6) {						
						level.remove(this);
					}
				}
			}
		}
		
		if(x >= Game.WIDTH || x <= 0 || y <= 0 || y >= Game.HEIGHT) {
			level.remove(this);
		}
	}

	@Override
	public void render(Graphics g) {
		g.setColor(Color.WHITE);
		g.fillRect(x, y, 2, 2);
	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle(x, y, 5, 5);
	}
}
