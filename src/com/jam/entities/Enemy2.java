package com.jam.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.jam.main.Game;
import com.jam.world.Camera;

public class Enemy2 extends Enemy{

	public Enemy2(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		this.speed = 0.5;
		this.maskx = 2;
		this.masky = 2;
		this.maskw = 15;
		this.maskh = 15;
		this.frames = 0;
		this.maxFrames = 5;
		this.index=0;
		this.maxIndex = 1;
		this.life = 4;
		this.sprites = new BufferedImage[2];
		sprites[0] = Game.spritesheet.getSprite(16*7, 16*2, 16, 16);
		sprites[1] = Game.spritesheet.getSprite((16*7)+16, 16*2, 16, 16);
	}
	
	@Override
	public void render(Graphics g) {
	if(!isDamaged) {
		g.drawImage(sprites[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
	}else
		g.drawImage(Entity.ENEMY_FEEDBACK2, this.getX() - Camera.x, this.getY() - Camera.y, null);

	}

}
