package com.jam.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
//import java.util.ArrayList;

//import com.jam.graficos.Spritesheet;
import com.jam.main.Game;
import com.jam.main.Sound;
import com.jam.world.Camera;
import com.jam.world.World;

public class Player extends Entity {

	public boolean right, up, left, down;
	public int right_dir = 0, left_dir = 1, up_dir = 2, down_dir = 3;
	public int dir = right_dir;
	public double speed = 1.6;

	private int frames = 0, maxFrames = 5, index = 0, maxIndex = 3;
	private boolean moved = false;
	private BufferedImage[] rightPlayer;
	private BufferedImage[] leftPlayer;
	private BufferedImage[] upPlayer;
	private BufferedImage[] downPlayer;

	private BufferedImage playerDamage;

	private boolean arma = false;

	public int ammo = 10;

	public boolean isDamaged = false;
	private int damageFrames = 0;

	public boolean shoot = false, mouseShoot = false;

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
			leftPlayer[i] = Game.spritesheet.getSprite(80 - (i * 16), 16, 16, 16);
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
		if (right && World.isFree((int) (x + speed), this.getY())) {
			moved = true;
			dir = right_dir;
			x += speed;
		} else if (left && World.isFree((int) (x - speed), this.getY())) {
			moved = true;
			dir = left_dir;
			x -= speed;
		}
		if (up && World.isFree(this.getX(), (int) (y - speed))) {
			moved = true;
			dir = up_dir;
			y -= speed;
		} else if (down && World.isFree(this.getX(), (int) (y + speed))) {
			moved = true;
			dir = down_dir;
			y += speed;
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

		checkCollisionLifePack();
		checkCollisionAmmo();
		checkCollisionGun();

		if (isDamaged) {
			this.damageFrames++;
			if (this.damageFrames == 10) {
				this.damageFrames = 0;
				isDamaged = false;
			}

		}

		if (shoot) {
			shoot = false;
			if(arma && ammo > 0){
			Sound.shootEffect.play();
			ammo--;
			// Criar bala e atirar
			
			int dx = 0;
			int dy = 0;
			int px = 0;
			int py = 0;
			if (dir == right_dir) {
				px = 8;
				py = 18;
				dx = 1;
			} else if (dir == left_dir) {
				px = 8;
				py = -5;
				dx = -1;
			} else if (dir == down_dir) {
				px = 7;
				py = 0;
				dy = 1;
			} else if (dir == up_dir) {
				px = 4;
				py = 12;
				dy = -1;
			}

			if (dir == right_dir || dir == left_dir) {
				BulletShoot bullet = new BulletShoot(this.getX() + py, this.getY() + px, 3, 2, null, dx, dy);
				Game.bullet.add(bullet);
			} else {
				BulletShoot bullet = new BulletShoot(this.getX() + py, this.getY() + px, 2, 3, null, dx, dy);
				Game.bullet.add(bullet);
			}
			}
			// Game.bullets.add(bullet);
		}
		
		if(mouseShoot) {
			System.out.println("Atirou!");
			shoot = false;
			if(arma && ammo > 0){
			ammo--;
			
			// Criar bala e atirar
			
			int dx = 0;
			int dy = 0;
			int px = 8;
			int py = 8;
			
			if (dir == right_dir || dir == left_dir) {
				BulletShoot bullet = new BulletShoot(this.getX() + py, this.getY() + px, 3, 2, null, dx, dy);
				Game.bullet.add(bullet);
			} else {
				BulletShoot bullet = new BulletShoot(this.getX() + py, this.getY() + px, 2, 3, null, dx, dy);
				Game.bullet.add(bullet);
			}
			}
			// Game.bullets.add(bullet);
		}

		if (life <= 0) {
			Game.gameState = "GAMEOVER";	
			
		}

		updateCamera();

	}
	
	public void updateCamera() {
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

	public void checkCollisionGun() {
		for (int i = 0; i < Game.entities.size(); i++) {
			Entity e = Game.entities.get(i);
			if (e instanceof Weapon) {
				if (Entity.isColidding(this, e)) {
					arma = true;
					// System.out.println("segurando arma");
					Game.entities.remove(i);
					return;
				}
			}
		}
	}

	public void checkCollisionLifePack() {
		for (int i = 0; i < Game.entities.size(); i++) {
			Entity e = Game.entities.get(i);
			if (e instanceof Lifepack) {
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

	public void render(Graphics g) {
		if (!isDamaged) {
			if (dir == right_dir) {
				g.drawImage(rightPlayer[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
				if (arma) {
					// Desenhar arma para esquerda
					g.drawImage(Entity.GUN_RIGHT, this.getX() + 10 - Camera.x, this.getY() + 1 - Camera.y, null);

				}
			} else if (dir == left_dir) {
				g.drawImage(leftPlayer[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
				if (arma) {
					// Desenhar amar para esquerda
					g.drawImage(Entity.Gun_L, this.getX() - 10 - Camera.x, this.getY() + 1 - Camera.y, null);
				}
			} else if (dir == up_dir) {
				g.drawImage(upPlayer[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
			} else if (dir == down_dir) {
				g.drawImage(downPlayer[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
				if (arma) {
					// Desenhar arma para baixo
					g.drawImage(Entity.Gun_D, this.getX() - 7 - Camera.x, this.getY() + 3 - Camera.y, null);
				}
			}
		} else {
			g.drawImage(playerDamage, this.getX() - Camera.x, this.getY() - Camera.y, null);
			if (arma) {
				// Desenhar arma para baixo
				g.drawImage(Entity.Gun_D, this.getX() - 7 - Camera.x, this.getY() + 3 - Camera.y, null);
			}
		}
	}

}
