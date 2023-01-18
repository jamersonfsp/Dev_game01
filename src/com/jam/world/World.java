package com.jam.world;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.jam.entities.Bullet;
import com.jam.entities.Enemy;
import com.jam.entities.Entity;
import com.jam.entities.LifePack;
import com.jam.entities.Weapon;
import com.jam.main.Game;

public class World {

	public static Tiles[] tiles;
	public static int WIDTH, HEIGHT;
	public static final int TILE_SIZE = 16;

	public World(String path) {
		try {
			BufferedImage map = ImageIO.read(getClass().getResource(path));
			int[] pixels = new int[map.getWidth() * map.getHeight()];
			tiles = new Tiles[map.getWidth() * map.getHeight()];
			WIDTH = map.getWidth();
			HEIGHT = map.getHeight();
			map.getRGB(0, 0, map.getWidth(), map.getHeight(), pixels, 0, map.getTileWidth());

			for (int xx = 0; xx < map.getWidth(); xx++) {
				for (int yy = 0; yy < map.getHeight(); yy++) {
					int pixelAtual = pixels[xx + (yy * map.getWidth())];
					tiles[xx + (yy * WIDTH)] = new FloorTile(Tiles.TILE_FLOOR, yy * 16, xx * 16);
					if (pixelAtual == 0xFF000000) {
						tiles[xx + (yy * WIDTH)] = new FloorTile(Tiles.TILE_FLOOR, yy * 16, xx * 16);
					} else if (pixelAtual == 0xFFFFFFFF) {
						tiles[xx + (yy * WIDTH)] = new WallTile(Tiles.TILE_WALL, yy * 16, xx * 16);
					} else if (pixelAtual == 0xFF0F0F0F) {
						tiles[xx + (yy * WIDTH)] = new FloorTiles_2(Tiles.TILE_FLOOR2, yy * 16, xx * 16);
					} else if (pixelAtual == 0xFF0026FF) {
						// player
						Game.player.setX(xx * 16);
						Game.player.setY(yy * 16);
					} else if (pixelAtual == 0xFFFF0000) {
						Enemy en = new Enemy(xx * 16, yy * 16, 16, 16, Entity.ENEMY_EN);
						Game.entities.add(en);
						Game.enemies.add(en);
					//} else if (pixelAtual == 0xFFFA0000) {
						// enemy dois
						//Enemy en = new Enemy2(xx * 16, yy * 16, 16, 16, Entity.ENEMY_EN2);
						//Game.entities.add(en);
						//Game.enemies.add(en);
					} else if (pixelAtual == 0xFFFF6A00) {

						Game.entities.add(new Weapon(xx * 16, yy * 16, 16, 16, Entity.WEAPON_EN));
					} else if (pixelAtual == 0xFFB6FF00) {
						Game.entities.add(new Bullet(xx * 16, yy * 16, 16, 16, Entity.BULLET_EN));
					} else if (pixelAtual == 0xFFFF7F7F) {
						Game.entities.add(new LifePack(xx * 16, yy * 16, 16, 16, Entity.LIFEPACK_EN));
					}

				}
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static boolean isFree(int xnext, int ynext) {
		int x1 = (xnext-1) / TILE_SIZE;
		int y1 = (ynext) / TILE_SIZE;

		int x2 = (xnext+TILE_SIZE-1)/TILE_SIZE;
		int y2 = (ynext) / TILE_SIZE;

		int x3 = (xnext-1) / TILE_SIZE;
		int y3 = (ynext+TILE_SIZE-2)/TILE_SIZE;


		int x4 = (xnext+TILE_SIZE-1)/TILE_SIZE;
		int y4 = (ynext+TILE_SIZE-2)/TILE_SIZE;

		return !(tiles[x1 + (y1 * World.WIDTH)] instanceof WallTile
				|| tiles[x2 + (y2 * World.WIDTH)] instanceof WallTile
				|| tiles[x3 + (y3 * World.WIDTH)] instanceof WallTile
				|| tiles[x4 + (y4 * World.WIDTH)] instanceof WallTile);
	}

	public void render(Graphics g) {

		int xstart = Camera.x >> 4;
		int ystart = Camera.y >> 4;

		int xfinal = xstart + (Game.WIDTH >> 4) + 1;
		int yfinal = ystart + (Game.HEIGHT >> 4) + 1;

		for (int xx = xstart; xx < xfinal; xx++) {
			for (int yy = ystart; yy < yfinal; yy++) {
				if (xx < 0 || yy < 0 || xx >= WIDTH || yy >= HEIGHT)
					continue;
				Tiles tile = tiles[xx + (yy * WIDTH)];
				tile.render(g);
			}
		}
	}

}
