package com.jam.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class Menu {

	public String[] options = { "novo", "carregar", "sair" };

	public int currentOption = 0;
	public int maxOption = options.length - 1;

	public boolean up, down, enter;
	public boolean pause =false;

	public void tick() {
		if (up) {
			currentOption--;
			if (currentOption < 0) {
				currentOption = maxOption;
			}
			up = false;
		} else if (down) {
			currentOption++;
			if (currentOption > maxOption) {
				currentOption = 0;
			}
			down = false;
		}
		if(enter) {
			enter = false;
			if(options[currentOption] == "novo" || options[currentOption]== "continuar") {
				Game.gameState = "NORMAL";
				pause = false;
			}else if(options[currentOption] == "carregar") {
				
			}else {
				System.exit(1);
			}
				
		}
	}

	public void render(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(new Color(0,0,0,100));
		//g.setColor(Color.black);
		g2.fillRect(0, 0, Game.WIDTH * Game.SCALE, Game.HEIGHT * Game.SCALE);
		g.setColor(Color.white);
		g.setFont(new Font("arial", Font.BOLD,36));
		g.drawString(">Game_01<", (Game.WIDTH * Game.SCALE / 8) * 3-30, (Game.HEIGHT * Game.SCALE / 11) * 2);
	
		g.setFont(new Font("arial", Font.BOLD,24));
		if(pause == false) {
			g.drawString("Novo jogo", (Game.WIDTH * Game.SCALE / 8) * 3, (Game.HEIGHT * Game.SCALE / 11) * 4);
		}else
			g.drawString("Cotinuar", (Game.WIDTH * Game.SCALE / 8) * 3, (Game.HEIGHT * Game.SCALE / 11) * 4);
		
		g.drawString("Carregar", (Game.WIDTH * Game.SCALE / 8) * 3, (Game.HEIGHT * Game.SCALE / 11) * 4+35);
		g.drawString("Sair", (Game.WIDTH * Game.SCALE / 8) * 3, (Game.HEIGHT * Game.SCALE / 11) * 4+70);
		
		if(options[currentOption]=="novo") {
			g.drawString(">", (Game.WIDTH * Game.SCALE / 8) * 3 - 20, (Game.HEIGHT * Game.SCALE / 11) * 4);
		}else if(options[currentOption]=="carregar") {
			g.drawString(">", (Game.WIDTH * Game.SCALE / 8) * 3 - 20, (Game.HEIGHT * Game.SCALE / 11) * 4+35);
		}else if (options[currentOption]=="sair")
			g.drawString(">", (Game.WIDTH * Game.SCALE / 8) * 3-20, (Game.HEIGHT * Game.SCALE / 11) * 4+70);
	}

}
