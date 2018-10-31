package Main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import Input.Keyboard;
import Sounds.Jukebox;
import Sprites.BufferedImageLoader;
import Sprites.SpriteSheet;

public class MenuScreen {

	private Game game;
	@SuppressWarnings("unused")
	private SpriteSheet sheet;
	private BufferedImage title = null;
	
	private int time;
	
	public MenuScreen(Game game) {
		this.game = game;
		BufferedImageLoader loader = new BufferedImageLoader();
		title = loader.loadImage("/title.png");
		sheet = new SpriteSheet(title);
	}
	
	public void render(Graphics g) {
		g.drawImage(title, game.getWidth()/5, 50, null);
		g.setColor(Color.WHITE);
		Font font = new Font("Courier New", 1, 16);
		g.setFont(font);
		g.drawString("PRESS X TO START", game.getWidth()/3 + 16, (int) (game.getHeight() - Math.abs(Math.sin(time * 0.1) * 7) - 80));
		g.drawString("Made by: teoboyhaha", game.getWidth()/5, 250);
	}
	
	public void update(Keyboard key) {
		if(game.state == STATE.Menu) {
			time++;
			if(key.FIRE) {
				game.state = STATE.Game;
				Jukebox.getSound("select").play();
			}
		}
	}
}
