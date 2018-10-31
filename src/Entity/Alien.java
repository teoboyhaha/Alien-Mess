package Entity;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import Level.Level;
import Particles.ParticleSpawner;
import Sounds.Jukebox;
import Sprites.Animation;
import Sprites.BufferedImageLoader;
import Sprites.SpriteSheet;

public class Alien extends Entity {

	private Player player;
	private Barrier barrier;
	private Bullet bullet;
	private Level level;
	
	private BufferedImage enemy_image_right = null;
	private BufferedImage enemy_image_left = null;
	private Animation animationRight;
	private Animation animationLeft;
	private BufferedImage[] enemy_mation_right = new BufferedImage[3];
	private BufferedImage[] enemy_mation_left = new BufferedImage[3];
	
	private int width = 32;
	private int height = 32;
	protected int health = 3;
	private float gravity = 0.5f;
	private boolean falling = true;
	private boolean facing = true;
	public boolean hit = false;
		
	public Alien(int x, int y, ID id, Player player, Barrier barrier, Level level, SpriteSheet sheet,
			Bullet bullet) {
		
		super(x, y, id, sheet);
		this.player = player;
		this.barrier = barrier;
		this.level = level;
		this.bullet = bullet;
		
		BufferedImageLoader loader = new BufferedImageLoader();
		sprite_sheet = loader.loadImage("/enemy.png");
		sheet = new SpriteSheet(sprite_sheet);
		enemy_image_right = sheet.grabSubImage(1, 1, 16, 16);
		enemy_image_left = sheet.grabSubImage(1, 2, 16, 16);
		
		enemy_mation_right[0] = sheet.grabSubImage(2, 1, 16, 16);
		enemy_mation_right[1] = sheet.grabSubImage(3, 1, 16, 16);
		enemy_mation_right[2] = sheet.grabSubImage(4, 1, 16, 16);
		animationRight = new Animation(3, enemy_mation_right[0], enemy_mation_right[1], enemy_mation_right[2]);
		
		enemy_mation_left[0] = sheet.grabSubImage(2, 2, 16, 16);
		enemy_mation_left[1] = sheet.grabSubImage(3, 2, 16, 16);
		enemy_mation_left[2] = sheet.grabSubImage(4, 2, 16, 16);
		animationLeft = new Animation(3, enemy_mation_left[0], enemy_mation_left[1], enemy_mation_left[2]);
		
	}

	@Override
	public void update() {
				
		int speed = 2;
		
		x += velX;
		y += velY;
		
		goTo(speed);
		
		if(falling) {
			velY += gravity;
			if(velY > 8.5) {
				velY = (float) 8.5;
			}
		}
		
		if(health <= 0) {
			player.money+=5;
			Jukebox.getSound("alien_death").play();
			level.remove(this);
		}
		
		animationRight.runAnimation();
		animationLeft.runAnimation();
		getCollision();
		
	}

	@Override
	public void render(Graphics g) {
		if(facing) {
			if(velX == 0) {	
				g.drawImage(enemy_image_right, x, y, width, height, null);
			} else {
				animationRight.drawAnimation(g, x, y, width, height, 0);
			}
		} else {
			if(velX == 0) {				
				g.drawImage(enemy_image_left, x, y, width, height, null);
			} else {
				animationLeft.drawAnimation(g, x, y, width, height, 0);
			}		
		}
	}
	
	private void goTo(int speed) {
		for(int i = 0; i < level.entityList.size(); i++) {		
			Entity entity = level.entityList.get(i);
			if(entity.getId() == ID.Barrier) {
				if(x < barrier.getX()) { velX=speed; facing = true; }
				if(x > barrier.getX()) { velX=-speed; facing = false; }
			} else {
				if(x < player.getX()) { velX=speed; facing = true; }
				if(x > player.getX()) { velX=-speed; facing = false; }
			}
		}
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
			} else if(entity.getId() == ID.Player) {
				if(getBounds().intersects(entity.getBounds())) {
					velX = 0;
					velY = 0;
				}
			} else if(entity.getId() == ID.Barrier) {
				if(getBoundsRight().intersects(entity.getBounds())) {
					velX = 0;
					velY = 0;
				}
				if(getBoundsLeft().intersects(entity.getBounds())) {
					velX = 0;
					velY = 0;
				}
			} else if(entity.getId() == ID.Bullet) {
				if(getBoundsRight().intersects(entity.getBounds()) || getBoundsLeft().intersects(entity.getBounds())) {
					health-=1;
					level.remove(bullet);
					new ParticleSpawner(x, y, 4, 4, level, new Color(50, 142, 23));
				}
			}
		}
	}
	
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
		return new Rectangle(x+width-2, y, 2, height-1);
	}

	public Rectangle getBoundsLeft() {
		return new Rectangle(x, y, 2, height-1);
	}
}
