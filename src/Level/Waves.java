package Level;

import Entity.Alien;
import Entity.ID;
import Main.Game;

public class Waves {

	private Level level;
	private Game game;
	
	public int wave = 1;
	private int time;
	private int time2;
	private int time3;
	private int time4;
	
	public Waves(Level level, Game game) {
		this.level = level;
		this.game = game;
	}
	
	public void update() {
		time++;
		time2++;
		time3++;
		time4++;
		if(time >= 250) {
			time = 0;
			for(int i = 0; i < 1; i++) {
				level.add(new Alien(-60, 300, ID.Alien, game.player, game.barrier, level, game.sheet, game.bullet));
				level.add(new Alien(815, 300, ID.Alien, game.player, game.barrier, level, game.sheet, game.bullet));
			}
		}
		if(time2 >= 500) {
			time2 = 0;
			for(int i = 0; i < 3; i++) {
				level.add(new Alien(-60, 300, ID.Alien, game.player, game.barrier, level, game.sheet, game.bullet));
				level.add(new Alien(815, 300, ID.Alien, game.player, game.barrier, level, game.sheet, game.bullet));
				wave++;
			}
		}
		if(time3 >= 12000) {
			time3 = 0;
			for(int i = 0; i < 10; i++) {
				level.add(new Alien(-60, 300, ID.Alien, game.player, game.barrier, level, game.sheet, game.bullet));
				level.add(new Alien(815, 300, ID.Alien, game.player, game.barrier, level, game.sheet, game.bullet));
			}
		}
		if(time4 >= 18000) {
			time4 = 0;
			for(int i = 0; i < 20; i++) {
				level.add(new Alien(-60, 300, ID.Alien, game.player, game.barrier, level, game.sheet, game.bullet));
				level.add(new Alien(815, 300, ID.Alien, game.player, game.barrier, level, game.sheet, game.bullet));
			}
		}
	}
}
