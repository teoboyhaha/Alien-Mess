package Entity;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import Level.Level;
import Particles.ParticleSpawner;
import Sounds.Jukebox;
import Sprites.BufferedImageLoader;
import Sprites.SpriteSheet;

public class Barrier extends Entity {

	private BufferedImage barrierTile = null;
	private Level level;
	
	public int health = 1000;
	private int width = 16;
	private int height = 64;
	
	public Barrier(int x, int y, ID id, Level level, SpriteSheet sheet, boolean facing) {
		super(x, y, id, sheet);
		this.level = level;
		BufferedImageLoader loader = new BufferedImageLoader();
		sprite_sheet = loader.loadImage("/tile.png");
		sheet = new SpriteSheet(sprite_sheet);
		if(!facing) {			
			barrierTile = sheet.grabSubImage(5, 1, 16, 48);
		} else {
			barrierTile = sheet.grabSubImage(6, 1, 16, 48);
		}
	}

	@Override
	public void update() {
		for(int i = 0; i < level.entityList.size(); i++) {
			Entity entity = level.entityList.get(i);
			if(entity.getId() == ID.Alien) {
				if(getBounds().intersects(entity.getBounds())) {
					health--;
					new ParticleSpawner(x, y, 1, 2, level, new Color(56, 23, 6));
					if(health <= 0) {
						Jukebox.getSound("destroyed").play();
						level.remove(this);
					}
				}
			}
		}
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(barrierTile, x, y, null);
		g.setColor(Color.GRAY);
		g.fillRect(x, y-32, width-1, 8);
		g.setColor(Color.GREEN);
		g.fillRect(x, y-32, (health/width)/4, 8);
	}
	
	
	
	@Override
	public Rectangle getBounds() {
		return new Rectangle(x, y, width, height);
	}
	
	public Rectangle getBoundsBottom() {
		return new Rectangle(x, y+height-3, width, 2);
	}
	
	public Rectangle getBoundsTop() {
		return new Rectangle(x-1, y, width, 2);
	}
	
	public Rectangle getBoundsRight() {
		return new Rectangle(x+width-4, y, 2, height-1);
	}

	public Rectangle getBoundsLeft() {
		return new Rectangle(x, y, 2, height-1);
	}
}
