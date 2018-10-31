package Main;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import Entity.Alien;
import Entity.Barrier;
import Entity.Bullet;
import Entity.ID;
import Entity.Player;
import Input.Keyboard;
import Input.Mouse;
import Level.Level;
import Level.Waves;
import Sounds.Jukebox;
import Sprites.BufferedImageLoader;
import Sprites.SpriteSheet;

public class Game extends Canvas implements Runnable {

	private static final long serialVersionUID = 1L;

	public static final int WIDTH = 800;
	public static final int HEIGHT = WIDTH / 16 * 9;
	
	private Thread thread;
	private Level level;
	private Keyboard key;
	private Mouse mouse;
	public SpriteSheet sheet;
	private Waves wave;
	private MenuScreen menu;
	private DeathScreen death;
	
	public Player player;
	public Barrier barrier;
	public Barrier barrier2;
	public Bullet bullet;
	
	private boolean running = false;
	
	private BufferedImage moon = null;
	private BufferedImage stars = null;
	
	public STATE state = STATE.Menu;
	
	public Game() {
		
		new Window(WIDTH, HEIGHT, "Alien Mess", this);
		
		level = new Level();
		key = new Keyboard();
		mouse = new Mouse();
		wave = new Waves(level, this);
		menu = new MenuScreen(this);
		death = new DeathScreen(this, wave);
		
		this.addMouseListener(mouse);
		this.addMouseMotionListener(mouse);
		this.addKeyListener(key);
		
		Jukebox.load();
		init();
		start();
		
	}
	
	public void init() {
		
		barrier = new Barrier(150, 345, ID.Barrier, level, sheet, false);
		barrier2 = new Barrier(600, 345, ID.Barrier, level, sheet, true);
		player = new Player(WIDTH/2, 240, ID.Player, this, key, level, sheet, wave);
			
		level.loadLevel();
		level.add(player);
		level.add(barrier);
		level.add(barrier2);
		level.add(new Alien(-60, 320, ID.Alien, player, barrier, level, sheet, bullet));
		level.add(new Alien(815, 320, ID.Alien, player, barrier, level, sheet, bullet));
		
		BufferedImageLoader loader = new BufferedImageLoader();
		moon = loader.loadImage("/moon.png");
		stars = loader.loadImage("/stars.png");
		sheet = new SpriteSheet(moon);
		sheet = new SpriteSheet(stars);
	}
	
	public synchronized void start() {
		running = true;
		
		thread = new Thread(this, "Display");
		thread.start();
	}
	
	public synchronized void stop() {
		running = false;
		
		try {
			thread.join();
		} catch(InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void run() {
		long lastTime = System.nanoTime();
		long timer = System.currentTimeMillis();
		final double ns = 1000000000.0 / 60.0;
		double delta = 0;
		
		int frames = 0;
		int updates = 0;
		
		this.requestFocus();
		
		while(running) {
			
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			
			while(delta >= 1) {
				update();
				updates++;
				delta--;
			}
			
			render();
			frames++;
			
			if(System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				System.out.println(updates + " UPDATES, " + frames + " FPS");
				updates = 0;
				frames = 0;
			}
		}
	}
	
	public void update() {
		key.update();
		if(state == STATE.Game) {			
			level.update();
			wave.update();
		} else if(state == STATE.Menu) {
			menu.update(key);
		} else if (state == STATE.GameOver) {
			death.update(key);			
		}
	}
	
	public void render() {
		
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null) {
			this.createBufferStrategy(3);
			return;
		}
				
		Graphics g = bs.getDrawGraphics();
		
		g.fillRect(0, 0, getWidth(), getHeight());
		
		if(state == STATE.Game) {	
			g.drawImage(stars, 0, 0, null);
			g.drawImage(moon, 700, 50, null);
			level.render(g);
		} else if(state == STATE.Menu) {
			menu.render(g);
		} else if (state == STATE.GameOver) {
			death.render(g);			
		}
		
		g.dispose();
		bs.show();
		
	}
	
	public static void main(String args[]) {
		new Game();
	}
}
