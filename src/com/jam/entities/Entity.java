package com.jam.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import com.jam.main.Game;
import com.jam.world.Camera;

public class Entity {
	
	public static BufferedImage LIFEPACK_EN = Game.spritesheet.getSprite(16*6, 0, 16, 16);
	public static BufferedImage WEAPON_EN = Game.spritesheet.getSprite(16*7, 0, 16, 16);
	public static BufferedImage BULLET_EN = Game.spritesheet.getSprite(16*6, 16, 16, 16);
	public static BufferedImage ENEMY_EN = Game.spritesheet.getSprite(16*7, 16, 16, 16);
	public static BufferedImage ENEMY_EN2 = Game.spritesheet.getSprite(16*7, 16*2, 16, 16);
	public static BufferedImage GUN_RIGHT = Game.spritesheet.getSprite(128, 0, 16, 16);
	public static BufferedImage Gun_L = Game.spritesheet.getSprite(144, 0, 16, 16);
	public static BufferedImage Gun_D = Game.spritesheet.getSprite(144, 16, 16, 16);
	
	protected double x;
	protected double y;
	protected int width;
	protected int height;
	
	public boolean debug = false;
	
	private BufferedImage sprite;
	
	private int maskx,masky,mwidth, mheight;

	public Entity(int x, int y, int width, int height, BufferedImage sprite) {
		super();
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.sprite = sprite;
		
		this.maskx = 0;
		this.setMasky(0);
		this.mwidth= width;
		this.mheight=height;
	}
	
	public void setMask(int maskx, int masky, int mwidth, int mheight) {
		this.maskx=maskx;
		this.setMasky(masky);
		this.mwidth=mwidth;
		this.mheight=mheight;
	}

	public int getX() {
		return (int)x;
	}

	public void setX(int NewX) {
		this.x = NewX;
	}

	public int getY() {
		return (int)y;
	}

	public void setY(int NewY) {
		this.y = NewY;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}
	
	public void tick() {
		
	}
	
	public static boolean isColidding(Entity e1, Entity e2) {
		Rectangle e1Mask = new Rectangle(e1.getX()+e1.maskx,e1.getY()+e1.height,e1.mwidth,e1.mheight);
		Rectangle e2Mask = new Rectangle(e2.getX()+e2.maskx,e2.getY()+e2.height,e2.mwidth,e2.mheight);
		
		return e1Mask.intersects(e2Mask);
	}
	
	public void render(Graphics g) {
		g.drawImage(sprite, this.getX() - Camera.x,this.getY() - Camera.y, null);
		//g.setColor(Color.RED);
		//g.fillRect(this.getX()+maskx-Camera.x, this.getY() +masky- Camera.y, mwidth, mheight);
	}

	public int getMasky() {
		return masky;
	}

	public void setMasky(int masky) {
		this.masky = masky;
	}

	
}
