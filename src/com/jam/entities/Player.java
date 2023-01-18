package com.jam.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import com.jam.graficos.Spritesheet;
import com.jam.main.Game;
import com.jam.world.Camera;
import com.jam.world.World;

public class Player extends Entity {

	public boolean right, up, left, down;
	public int right_dir = 0, left_dir = 1, up_dir = 2, down_dir = 3;
	public int dir = right_dir;
	public double speed = 2.0;

	private int frames = 0, maxFrames = 5, index = 0, maxIndex = 3;
	private boolean moved = false;
	private BufferedImage[] rightPlayer;
	private BufferedImage[] leftPlayer;
	private BufferedImage[] upPlayer;
	private BufferedImage[] downPlayer;

	private BufferedImage playerDamage;

	public boolean isDamaged = false;
	private int damageFrames = 0;

	public boolean shoot = false;

	private boolean arma = false;

	public int ammo = 5;

	public double life = 100, maxLife = 100;

	public Player(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		// TODO Auto-generated constructor stub
		rightPlayer = new BufferedImage[4];
		leftPlayer = new BufferedImage[4];
		upPlayer = new BufferedImage[4];
		downPlayer = new BufferedImage[4];

		playerDamage = Game.spritesheet.getSprite(0, 16, 16, 16);

		for (int i = 0; i < 4; i++) {
			rightPlayer[i] = Game.spritesheet.getSprite(32 + (i * 16), 0, 16, 16);
		}
		for (int i = 0; i < 4; i++) {
			leftPlayer[i] = Game.spritesheet.getSprite(32 + (i * 16), 16, 16, 16);
		}
		for (int i = 0; i < 4; i++) {
			upPlayer[i] = Game.spritesheet.getSprite(32 + (i * 16), 48, 16, 16);
		}
		for (int i = 0; i < 4; i++) {
			downPlayer[i] = Game.spritesheet.getSprite(32 + (i * 16), 32, 16, 16);
		}

	}

	public void tick() {
		moved = false;
		if (right && World.isFree(this.getX() + (int) speed, this.getY())) {
			moved = true;
			dir = right_dir;
			x += speed;
		} else if (left && World.isFree(this.getX() - (int) speed, this.getY())) {
			moved = true;
			dir = left_dir;
			x -= speed;
		}

		if (down && World.isFree(this.getX(), this.getY() + (int) speed)) {
			moved = true;
			dir = down_dir;
			y += speed;
		} else if (up && World.isFree(this.getX(), this.getY() - (int) speed)) {
			moved = true;
			dir = up_dir;
			y -= speed;
		}

		if (moved) {
			frames++;
			if (frames == maxFrames) {
				frames = 0;
				index++;
				if (index > maxIndex)
					index = 0;
			}
		}

		checkItems();// Colisão entre o lifepack
		checkCollisionAmmo();
		checkCollisionArma();

		if (isDamaged) {
			this.damageFrames++;
			if (this.damageFrames == 10) {
				this.damageFrames = 0;
				isDamaged = false;
			}
		}

		if (shoot && arma & ammo > 0) {
			shoot = false;
			int dx = 0;
			int dy = 0;
			int px = 0;
			int py = 0;
			int w = 0;
			int h = 0;
			if (dir == right_dir) {
				px = 17;
				py = 8;
				w = 2;
				h=2;
				dx = 1;
			} else if (dir == left_dir) {
				px = -4;
				py = 8;
				w = 2;
				h=2;
				dx = -1;
			} else if (dir == down_dir) {
				w = 1;
				h=2;
				px = 2;
				py = 8;
				dy = 1;
				
			} else {
				w = 1;
				h=2;
				px = 10;
				py = 0;
				dy = -1;
			}

			BulletShoot bullet = new BulletShoot(this.getX()+px, this.getY()+py, w, h, null, dx, dy);
			Game.bullet.add(bullet);
			
			ammo = ammo-1;
		}

		if (life <= 0) {
			Game.entities = new ArrayList<Entity>();
			Game.enemies = new ArrayList<Enemy>();
			Game.spritesheet = new Spritesheet("/spritesheet.png");
			Game.player = new Player(0, 0, 16, 16, Game.spritesheet.getSprite(32, 0, 16, 16));
			Game.entities.add(Game.player);
			Game.world = new World("/map.png");
			return;
		}

		Camera.x = Camera.clamp(this.getX() - (Game.WIDTH / 2), 0, World.WIDTH * 16 - Game.WIDTH);
		Camera.y = Camera.clamp(this.getY() - (Game.HEIGHT / 2), 0, World.HEIGHT * 16 - Game.HEIGHT);

	}

	public void checkCollisionAmmo() {
		for (int i = 0; i < Game.entities.size(); i++) {
			Entity e = Game.entities.get(i);
			if (e instanceof Bullet) {
				if (Entity.isColidding(this, e)) {
					ammo += 5;
					Game.entities.remove(i);
					return;
				}
			}
		}
	}

	public void checkCollisionArma() {
		for (int i = 0; i < Game.entities.size(); i++) {
			Entity e = Game.entities.get(i);
			if (e instanceof Weapon) {
				if (Entity.isColidding(this, e)) {
					arma = true;
					Game.entities.remove(i);
					return;
				}
			}
		}
	}

	public void checkItems() {
		for (int i = 0; i < Game.entities.size(); i++) {
			Entity e = Game.entities.get(i);
			if (e instanceof LifePack) {
				if (Entity.isColidding(this, e)) {
					life += 8;
					if (life >= 100)
						life = 100;
					Game.entities.remove(i);
					return;
				}
			}
		}
	}

	// public boolean isColliding() {

	// }

	public void render(Graphics g) {
		if (!isDamaged) {
			if (dir == right_dir) {
				g.drawImage(rightPlayer[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
				if (arma) {
					g.drawImage(GUN_RIGHT, this.getX() + 10 - Camera.x, this.getY() + 1 - Camera.y, null);
				}
			} else if (dir == left_dir) {
				g.drawImage(leftPlayer[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
				if (arma) {
					g.drawImage(Gun_L, this.getX() - 10 - Camera.x, this.getY() + 1 - Camera.y, null);
				}
			} else if (dir == up_dir) {
				g.drawImage(upPlayer[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
				if (arma) {

				}
			} else if (dir == down_dir) {
				g.drawImage(downPlayer[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
				if (arma) {
					g.drawImage(Gun_D, this.getX() - 6 - Camera.x, this.getY() + 3 - Camera.y, null);
				}
			}
		} else {
			if (arma) {
				g.drawImage(playerDamage, this.getX() - Camera.x, this.getY() - Camera.y, null);
				g.drawImage(Gun_D, this.getX() - 6 - Camera.x, this.getY() + 3 - Camera.y, null);
			} else
				g.drawImage(playerDamage, this.getX() - Camera.x, this.getY() - Camera.y, null);
		}

	}
}
