package ch.coredump.asteroids.entities;

import processing.core.PApplet;

public abstract class BaseEntity {
	protected float x = 0;
	protected float y = 0;

	protected float speed = 3;

	public BaseEntity(float x, float y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Draws the entity on screen
	 * 
	 * @param processing
	 */
	public void render(PApplet processing) {
		// default white circle
		processing.fill(255);
		processing.circle(x, y, 25);

		processing.noFill();

	}

	/**
	 * Update game logic
	 * 
	 * @param tpf time per frame in ms.
	 */
	abstract public void update(float tpf);

	public void setPosition(float x, float y) {
		this.x = x;
		this.y = y;
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}
}
