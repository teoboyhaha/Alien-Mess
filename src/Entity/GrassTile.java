package Entity;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import Level.Level;
import Sprites.BufferedImageLoader;
import Sprites.SpriteSheet;

public class GrassTile extends Entity {
	
	private BufferedImage grassTile = null;
	
	public GrassTile(int x, int y, ID id, Level level, SpriteSheet sheet) {
		super(x, y, id, sheet);
		BufferedImageLoader loader = new BufferedImageLoader();
		sprite_sheet = loader.loadImage("/tile.png");
		sheet = new SpriteSheet(sprite_sheet);
		grassTile = sheet.grabSubImage(3, 1, 32, 32);
	}

	@Override
	public void update() {
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(grassTile, x, y, null);
	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle(x, y, 32, 32);
	}
}
