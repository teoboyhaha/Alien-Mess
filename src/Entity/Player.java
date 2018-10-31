package Entity;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import Input.Keyboard;
import Level.Level;
import Level.Waves;
import Main.Game;
import Main.STATE;
import Particles.ParticleSpawner;
import Sounds.Jukebox;
import Sprites.Animation;
import Sprites.BufferedImageLoader;
import Sprites.SpriteSheet;

public class Player extends Entity {

	private Keyboard key;
	private Level level;
	private SpriteSheet sheet;
	private Game game;
	private Waves wave;
	
	private BufferedImage player_image_right = null;
	private BufferedImage player_image_left = null;
	private Animation animationRight;
	private Animation animationLeft;
	private BufferedImage[] player_mation_right = new BufferedImage[3];
	private BufferedImage[] player_mation_left = new BufferedImage[3];
	
	private int width = 32;
	private int height = 32;
	protected float health = 1000;
	private int time = 0;
	
	public int money = 300;
	private int lastBullets;
	private int maxMag = 12;
	private int maxBullets = 50;
	private int bullets = 12;
	private int timeReload;
	private float gravity = 0.7f;
	private boolean falling = true;
	private boolean jumping = true;
	private boolean fire = true;
	private boolean facing;
	
	private enum GunSTATE {
		Pistol,
		Rifle,
		Shotgun;
	}
	
	private GunSTATE gunState = GunSTATE.Pistol;
	
	public Player(int x, int y, ID id, Game game, Keyboard key, Level level, SpriteSheet sheet,
			Waves wave) {
		
		super(x, y, id, sheet);
		this.level = level;
		this.key = key;
		this.game = game;
		this.wave = wave;
		
		BufferedImageLoader loader = new BufferedImageLoader();
		sprite_sheet = loader.loadImage("/player.png");
		sheet = new SpriteSheet(sprite_sheet);
		player_image_right = sheet.grabSubImage(1, 1, 16, 16);
		player_image_left = sheet.grabSubImage(1, 2, 16, 16);
		
		player_mation_right[0] = sheet.grabSubImage(2, 1, 16, 16);
		player_mation_right[1] = sheet.grabSubImage(3, 1, 16, 16);
		player_mation_right[2] = sheet.grabSubImage(4, 1, 16, 16);
		animationRight = new Animation(3, player_mation_right[0], player_mation_right[1], player_mation_right[2]);
		
		player_mation_left[0] = sheet.grabSubImage(2, 2, 16, 16);
		player_mation_left[1] = sheet.grabSubImage(3, 2, 16, 16);
		player_mation_left[2] = sheet.grabSubImage(4, 2, 16, 16);
		animationLeft = new Animation(3, player_mation_left[0], player_mation_left[1], player_mation_left[2]);
		
	}

	@Override
	public void update() {
				
		int speed = 4;
		
		time++;
		timeReload++;
		if(time >= 61) {
			time = 0;
		}
		
		if(timeReload >= 181) {
			timeReload = 0;
		}
		
		if(maxBullets != 0) {
			lastBullets = bullets;
			if(timeReload >= 180 && bullets == 0) {					
				maxBullets -= maxMag - lastBullets;
				bullets = maxMag - bullets;
				lastBullets = 0;
			}
			
			if(maxBullets <= 0) {
				maxBullets = 0;
			}
		}
				
		x += velX;
		y += velY;

		if(key.right) { velX = speed; facing = true; }
		else if(!key.left) { velX = 0; }
		if(key.left) { velX = -speed; facing = false; }
		else if(!key.right) { velX = 0; }
		
		for(int i = 0; i < level.entityList.size(); i++) {
			if(key.REPAIR && money >= 200) {
				game.barrier.health = 1000;
				game.barrier2.health = 1000;
				money -= 200;
			} 
//			else if(!level.entityList.contains(game.barrier)) {
//				
//			} else if(!level.entityList.contains(game.barrier2)) {
//				
//			}
		}
		
		if(key.AMMO && money >= 100) { maxBullets += 50; money -= 100; }
		if(key.HEALTH && money >= 100) { health = 1000; money -= 100; }
		
		if(key.JUMP && !jumping) {
			jumping = true; 
			velY -= 7;
		}
		
		if(falling || jumping) {
			velY += gravity;
			if(velY > 8.5) {
				velY = (float) 8.5;
			}
		}
		
		animationRight.runAnimation();
		animationLeft.runAnimation();
		getCollision();
		shoot();
		
	}

	@Override
	public void render(Graphics g) {
		
		if(facing) {			
			if(velX == 0) {	
				g.drawImage(player_image_right, x, y, width, height, null);				
			} else {
				animationRight.drawAnimation(g, x, y, width, height, 0);
			}
		} else {
			if(velX == 0) {	
				g.drawImage(player_image_left, x, y, width, height, null);				
			} else {
				animationLeft.drawAnimation(g, x, y, width, height, 0);
			}
		}
		
		g.setColor(Color.GRAY);
		g.fillRect(5, 5, 200, 32);
		g.setColor(Color.GREEN);
		g.fillRect(5, 5, (int) (health*2)/10, 32);
		g.setColor(Color.DARK_GRAY);
		g.drawRect(5, 5, 200, 32);
		
		g.setColor(Color.WHITE);
		Font font = new Font("Courier", 1, 11);
		g.setFont(font);
		g.drawString(bullets + " / " + maxBullets + " bullets", 5, 54);
		g.drawString("$" + money, 5, 68);
		g.drawString("Wave: " + wave.wave, 5, 83);
		
	}
	
	private void getCollision() {
		for(int i = 0; i < level.entityList.size(); i++) {
			Entity entity = level.entityList.get(i);
			if(entity.getId() == ID.GrassTile) {
				if(getBoundsBottom().intersects(entity.getBounds())) {
					// Top
					if(getBoundsTop().intersects(entity.getBounds())) {
						y = entity.getY() + height;
						velY = 0;
					}
					// Bottom
					if(getBoundsBottom().intersects(entity.getBounds())) {
						y = entity.getY() - height;
						velY = 0;
						falling = false;
						jumping = false;
					} else {
						falling = true;
					}
					// Right
					if(getBoundsRight().intersects(entity.getBounds())) {
						x = entity.getX() - width;
					}
					// Left
					if(getBoundsLeft().intersects(entity.getBounds())) {
						x = entity.getX() + width;
					}
				}
			} else if(entity.getId() == ID.Alien) {
				if(getBounds().intersects(entity.getBounds())) {					
					health--;
					if(time >= 60) {						
						Jukebox.getSound("hurt").play();
						new ParticleSpawner(x, y, 4, 4, level, new Color(110, 2, 14));
					}
					if(health <= 0) {
						Jukebox.getSound("player_death").play();
						new ParticleSpawner(x, y, 15, 10, level, new Color(110, 2, 14));
						level.removeAll();
						game.state = STATE.GameOver;
					}
				}
			} else if(entity.getId() == ID.Barrier) {
				if(getBoundsLeft().intersects(entity.getBounds())) {					
					x = entity.getX() + width/2;
				}
				if(getBoundsRight().intersects(entity.getBounds())) {					
					x = entity.getX() - width;
				}
			}
		}
	}
	
	private void shoot() {
		if(key.FIRE && gunState == GunSTATE.Pistol && facing && fire) {
			if(bullets > 0) {				
				for(int i = 0; i < level.entityList.size(); i++) {		
					Entity entity = level.entityList.get(i);
					if(entity.getId() == ID.Player) {	
						Jukebox.getSound("gun").play();
						fire = false;
						level.add(new Bullet(entity.getX()+24, entity.getY()+6, 3, ID.Bullet, level, sheet));
						bullets--;
					}
				}
			}
		} else if(key.FIRE && gunState == GunSTATE.Pistol && !facing && fire) {
			if(bullets > 0) {				
				for(int i = 0; i < level.entityList.size(); i++) {		
					Entity entity = level.entityList.get(i);
					if(entity.getId() == ID.Player) {		
						Jukebox.getSound("gun").play();
						fire = false;
						level.add(new Bullet(entity.getX()+2, entity.getY()+6, -3, ID.Bullet, level, sheet));
						bullets--;
					}
				}
			}
		} else if(!key.FIRE && !fire) {
			fire = true;
		}
	}
	
	public Rectangle getBounds() {
		return new Rectangle(x, y, width, height);
	}
	
	public Rectangle getBoundsBottom() {
		return new Rectangle(x, y+height-4, width, 2);
	}
	
	public Rectangle getBoundsTop() {
		return new Rectangle(x-1, y, width, 2);
	}
	
	public Rectangle getBoundsRight() {
		return new Rectangle(x+width-3, y, 2, height-1);
	}

	public Rectangle getBoundsLeft() {
		return new Rectangle(x, y, 2, height-1);
	}
}
