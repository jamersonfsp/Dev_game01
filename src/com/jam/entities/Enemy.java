package com.jam.entities;

//import java.awt.Color;
//import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import com.jam.main.Game;
import com.jam.main.Sound;
import com.jam.world.Camera;
import com.jam.world.World;

public class Enemy extends Entity{
	
	protected double speed = 1;
	
	protected int maskx = 3, masky = 3, maskw = 13, maskh =13;
	protected int frames = 0, maxFrames = 10, index = 0, maxIndex = 1;
	protected BufferedImage[] sprites;
	
	protected int life = 8;
	protected boolean isDamaged = false;
	protected int damageFrames = 10, damageCurrent = 0;

	public Enemy(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, null);
		sprites = new BufferedImage[2];
		sprites[0] = Game.spritesheet.getSprite(112, 16, 16, 16);
		sprites[1] = Game.spritesheet.getSprite(112+16, 16, 16, 16);
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
				Sound.hurtEffect.play();
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
	
		
	public void render(Graphics g) {
		if(!isDamaged) {
		//g.fillRect(this.getX() + maskx - Camera.x, this.getY()+ masky - Camera.y, maskw, maskh);
		//super.render(g);
		//g.setColor(Color.blue);
		g.drawImage(sprites[index],this.getX()- Camera.x,this.getY()- Camera.y,null);
		//g.fillRect(this.getX() + maskx - Camera.x, this.getY()+ masky - Camera.y, maskw, maskh);
		//g.setColor(Color.blue);
		}else
			g.drawImage(Entity.ENEMY_FEEDBACK,this.getX()- Camera.x,this.getY()- Camera.y,null);
		
	}

}
