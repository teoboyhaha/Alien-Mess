package Particles;

import java.awt.Color;

import Entity.ID;
import Level.Level;
import Sprites.SpriteSheet;

public class ParticleSpawner {

	private SpriteSheet sheet;
	private int life;
	
	public ParticleSpawner(int x, int y, int life, int amount, Level level, Color color) {
		
		this.setLife(life);
		
		for(int i = 0; i < amount; i++) {
			level.add(new Particle(x, y, ID.Particle, sheet, level, life, color));
		}
	}
	
	public int getLife() {
		return life;
	}
	
	public void setLife(int life) {
		this.life = life;
	}
}
