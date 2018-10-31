package Level;

import java.awt.Graphics;
import java.util.LinkedList;

import Entity.Entity;
import Entity.GrassTile;
import Entity.ID;
import Main.Game;
import Sprites.SpriteSheet;

public class Level {

	public LinkedList<Entity> entityList = new LinkedList<Entity>();
	private SpriteSheet sheet;
	
	public void update() {
		for(int i = 0; i < entityList.size(); i++) {
			Entity entities = entityList.get(i);
			entities.update();
		}
	}
	
	public void render(Graphics g) {
		for(int i = 0; i < entityList.size(); i++) {
			Entity entities = entityList.get(i);
			entities.render(g);
		}
	}
	
	public void add(Entity entity) {
		entityList.add(entity);
	}
	
	public void remove(Entity entity) {
		entityList.remove(entity);
	}
	
	public void removeAll() {
		entityList.removeAll(entityList);
	}
	
	public void loadLevel() {
		for(int x = 0; x < Game.WIDTH; x += 32) {
			add(new GrassTile(x, Game.HEIGHT-56, ID.GrassTile, this, sheet));
		}
	}
}
