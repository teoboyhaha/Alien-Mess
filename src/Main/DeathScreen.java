package Main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import Input.Keyboard;
import Level.Waves;
import Sounds.Jukebox;
import Sprites.BufferedImageLoader;
import Sprites.SpriteSheet;

public class DeathScreen {

	private Game game;
	@SuppressWarnings("unused")
	private SpriteSheet sheet;
	private BufferedImage death = null;
	
	private int time;
	private Waves wave;
	
	public DeathScreen(Game game, Waves wave) {
		this.game = game;
		this.wave = wave;
		BufferedImageLoader loader = new BufferedImageLoader();
		death = loader.loadImage("/death.png");
		sheet = new SpriteSheet(death);
	}
	
	public void render(Graphics g) {
		g.drawImage(death, game.getWidth()/5, (int) (game.getHeight()/2 - Math.abs(Math.sin(time * 0.03) * 5)) - 70, null);
		g.setColor(Color.WHITE);
		Font font = new Font("Courier New", 1, 16);
		g.setFont(font);
		g.drawString("You Died on Wave, " + wave.wave, game.getWidth()/5 + 5, (int) (game.getHeight()/2 - Math.abs(Math.sin(time * 0.03) * 5)) + 35);
		g.drawString("PRESS X TO PLAY AGAIN", game.getWidth()/3 + 16, game.getHeight() - 110);
	}
	
	public void update(Keyboard key) {
		if(game.state == STATE.GameOver) {
			time++;
			if(key.FIRE) {
				game.state = STATE.Game;
				game.init();
				Jukebox.getSound("select").play();
			}
		}
	}
}
	