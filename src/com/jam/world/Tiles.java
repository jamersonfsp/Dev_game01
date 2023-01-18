package com.jam.world;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.jam.main.Game;

public class Tiles {
	
	public static BufferedImage TILE_FLOOR = Game.spritesheet.getSprite(0, 0, 16, 16);
	public static BufferedImage TILE_FLOOR2= Game.spritesheet.getSprite(0, 32, 16, 16);
	public static BufferedImage TILE_WALL =  Game.spritesheet.getSprite(16, 0, 16, 16);
	
	private BufferedImage sprite;
	private int x, y;
	
	public Tiles(int x, int y,BufferedImage sprite) {

		this.sprite = sprite;
		this.x = x;
		this.y = y;
	}

	public void render(Graphics g) {
		g.drawImage(sprite, x - Camera.x, y - Camera.y, null);
	}

}
