package ch.coredump.asteroids.ui;

import java.awt.Color;

import ch.coredump.asteroids.GameManager;
import ch.coredump.asteroids.gamestates.MainGameState;
import ch.coredump.asteroids.gamestates.MenuGameState;
import processing.core.PApplet;
import processing.core.PConstants;

/**
 * Rectangle is drawn in mode CENTER.
 */
public class Button {
	MenuGameState menu;
	float x;
	float y;
	float w;
	float h;
	boolean hover = false;

	Color bgColor = Color.white;
	String text;

	public Button(MenuGameState menu, float x, float y, float w, float h, String text) {
		this.menu = menu;
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;

		this.text = text;
	}

	public void update() {
		final float x1 = x - (w / 2);
		final float x2 = x1 + w;
		final float y1 = y - (h / 2);
		final float y2 = y1 + h;

		final PApplet p = menu.getProcessing();

		if (p.mouseX > x1 && p.mouseX < x2 && p.mouseY > y1 && p.mouseY < y2) {
			hover = true;
		} else {
			hover = false;
		}

		if (hover) {
			bgColor = Color.green;
		} else {
			bgColor = Color.white;
		}

		if (p.mousePressed && hover) {
			final GameManager manager = menu.getManager();
			manager.setGameState(new MainGameState(p, manager));
		}
	}

	public void render() {
		final PApplet p = menu.getProcessing();
		p.rectMode(PConstants.CENTER);
		p.fill(bgColor.getRGB());
		p.rect(x, y, w, h);

		// text inside button
		p.fill(Color.black.getRGB());
		p.text(text, x, y);
	}
}
