package com.jam.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import com.jam.main.Game;
import com.jam.world.Camera;
import com.jam.world.World;

public class Enemy extends Entity {

	private double speed = 0.8;
	private int maskx = 3, masky = 5, maskw = 12, maskh = 12;
	private int frames = 0, maxFrames = 5, index = 0, maxIndex = 1;
	private BufferedImage[] sprites;
	public int life = 1;

	public Enemy(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, null);
		sprites = new BufferedImage[2];
		sprites[0] = Game.spritesheet.getSprite(112, 16, 16, 16);
		sprites[1] = Game.spritesheet.getSprite(112 + 16, 16, 16, 16);
		// TODO Auto-generated constructor stub
	}
	
	public void tick() {
		//maskx = 8;
		//masky = 8;
		//maskw = 5;
		//maskh =5;
		if(this.isColiddingWithPlayer()==false) {
		if(Game.rand.nextInt(100)<80) {
		if((int)x<Game.player.getX() && World.isFree((int)(x+speed), this.getY())
				&& !isColidding((int)(x+speed), this.getY())) {
			x+=speed;
		}
		else if((int)x>Game.player.getX()&& World.isFree((int)(x-speed), this.getY())
				&& !isColidding((int)(x-speed), this.getY())) {
			x-=speed;
			
		}
		
		else if((int)y<Game.player.getY() && World.isFree(this.getX(), (int)(y+speed))
				&& !isColidding(this.getX(), (int)(y+speed))) {
			y+=speed;
		}
		else if((int)y>Game.player.getY()&& World.isFree(this.getX(), (int)(y-speed))
				&& !isColidding(this.getX(), (int)(y-speed))) {
			y-=speed;
			
		}
		}
		}else {
			//estamos colidindo
			if(Game.rand.nextInt(100)<10){
				Game.player.life-=Game.rand.nextInt(3);
				Game.player.isDamaged = true;
								
			}
		}
			frames++;
			if(frames == maxFrames) {
				frames = 0;
				index++;
				if(index > maxIndex) 
					index = 0;
				
			}
			
			collidingBullet();
			
			if(life <= 0) {
				destroySelf();
			}
			/*if(isDamaged) {
				this.damageCurrent++;
				if(this.damageCurrent == this.damageFrames) {
					this.damageCurrent = 0;
					this.isDamaged = false;
				}
			}*/
				
		
	}
	
	public void destroySelf() {
		Game.enemies.remove(this);
		Game.entities.remove(this);
		return;
	}
	
	public void collidingBullet() {
		for(int i = 0; i < Game.bullet.size(); i++) {
			Entity e = Game.bullet.get(i);
			if(e instanceof BulletShoot) {
				if(Entity.isColidding(this, e)) {
					//isDamaged = true;
					life-=4;
					Game.bullet.remove(i);
					return;
				}
			}
		}
	}
	
	public boolean isColiddingWithPlayer() {
		Rectangle enemyCurrent = new Rectangle(this.getX() + maskx,this.getY() + masky,maskw,maskh);
		Rectangle player = new Rectangle(Game.player.getX(),Game.player.getY(),16,16);
		return enemyCurrent.intersects(player);
	}
	
	public boolean isColidding(int xnext, int ynext) {
		Rectangle enemyCurrent = new Rectangle(xnext + maskx,ynext + masky,maskw,maskh);
		for(int i = 0; i < Game.enemies.size();i++) {
			Enemy e = Game.enemies.get(i);
			if(e == this) 
				continue;
			Rectangle targetEnemy = new Rectangle(e.getX() + maskx,e.getY()+masky,maskw,maskh);
			if(enemyCurrent.intersects(targetEnemy)) {
				return true;
			}
		}
		return false;
	}
	
	/*public void tick() {
		if (isPlayer(this.getX(), this.getY()) && isColiddingWithPlayer() == false) {
			if (Game.rand.nextInt(100) < 50) {
				if ((int) x < Game.player.getX() && World.isFree((int) (x + speed), this.getY())
						&& !isColidding((int) (x + speed), this.getY())) {
					x += speed;
				} else if ((int) x > Game.player.getX() && World.isFree((int) (x - speed), this.getY())
						&& !isColidding((int) (x - speed), this.getY())) {
					x -= speed;
				}
				if ((int) y < Game.player.getY() && World.isFree(this.getX(), (int) (y + speed))
						&& !isColidding(this.getX(), (int) (y + speed))) {
					y += speed;
				} else if ((int) y > Game.player.getY() && World.isFree(this.getX(), (int) (y - speed))
						&& !isColidding(this.getX(), (int) (y - speed))) {
					y -= speed;
				}
			}
		} else if (isColiddingWithPlayer() == true) {
			
			 //* if(Game.rand.nextInt(100)<10) { Game.player.life-=Game.rand.nextInt(5);
			 //* /if(Game.player.life <= 0) {
			 //* 
			 //* } System.out.println("Vida: "+Game.player.life); }
			 
			int forca = 1;
			perdeVida(forca);
		}
		if (isPlayer(this.getX(), this.getY()) == false) {
			if (Game.rand.nextInt(100) < 2 && World.isFree((int) (x + speed), this.getY())) {
				x += 1;
			}
			if (Game.rand.nextInt(100) < 2 && World.isFree((int) (x - speed), this.getY())) {
				y += 1;
			}
			if (Game.rand.nextInt(100) < 2 && World.isFree(this.getX(), (int) (y + speed))) {
				x -= 1;
			}
			if (Game.rand.nextInt(100) < 2 && World.isFree(this.getX(), (int) (y - speed))) {
				y -= 1;
			}

		}

		frames++;
		if (frames == maxFrames) {
			frames = 0;
			index++;
			if (index > maxIndex)
				index = 0;
		}

		collidingBullet();

		if (life <= 0) {
			destroySelf();
			return;
		}

	}

	public void destroySelf() {
		Game.enemies.remove(this);
		Game.entities.remove(this);
		return;
	}

	public void collidingBullet() {
		for (int i = 0; i < Game.bullet.size(); i++) {
			Entity e = Game.bullet.get(i);
			if (Entity.isColidding(this, e)) {
				System.out.println("Está colidindo");
				life--;
				Game.bullet.remove(i);
				return;
			}

		}
	}

	public int perdeVida(int forca) {
		if (Game.rand.nextInt(100) < 10) {
			Game.player.life -= (Game.rand.nextInt(3) + forca);
			Game.player.isDamaged = true;

			System.out.println("Vida: " + Game.player.life);
		}
		return (int) Game.player.life;
	}

	public boolean isColiddingWithPlayer() {
		Rectangle enemyCurrent = new Rectangle(this.getX() + maskx, this.getY() + masky, maskw, maskh);
		Rectangle player = new Rectangle(Game.player.getX(), Game.player.getY(), 16, 16);

		return enemyCurrent.intersects(player);
	}


	public boolean isPlayer(int xnext, int ynext) {
		Rectangle enemyCurrent = new Rectangle(this.getX() + maskx-50, this.getY()-50 + masky, maskw+100, maskh+100);
		Rectangle player = new Rectangle(Game.player.getX(), Game.player.getY(), 16, 16);

		return enemyCurrent.intersects(player);
	}
	

	public boolean isColidding(int xnext, int ynext) {
		Rectangle enemyCurrent = new Rectangle(xnext + maskx,ynext + masky,maskw,maskh);
		for(int i = 0; i < Game.enemies.size();i++) {
			Enemy e = Game.enemies.get(i);
			if(e == this) 
				continue;
			Rectangle targetEnemy = new Rectangle(e.getX() + maskx,e.getY()+masky,maskw,maskh);
			if(enemyCurrent.intersects(targetEnemy)) {
				return true;
			}
		}
		return false;
	}*/


	public void render(Graphics g) {
		g.drawImage(sprites[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
		g.setColor(Color.blue);
		// g.fillRect(this.getX() + maskx - Camera.x, this.getY() + masky-
		// Camera.y,maskw, maskh);
	}

}
