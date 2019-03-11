package ch.coredump.asteroids.entities;

import processing.core.PApplet;

public abstract class BaseEntity {
	private float x = 0;
	private float y = 0;
	protected float speed = 3;
	BoundingBox bb;
	boolean dead = false;

	public BaseEntity(float x, float y) {
		this.x = x;
		this.y = y;

		// default bounding box (size = 0)
		bb = new BoundingBox();
		bb.setPosition(x, y);
	}

	protected void setBoundingBoxSize(float x, float y) {
		bb.setSize(x, y);
	}

	public BoundingBox getBoundingBox() {
		return bb;
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

		bb.setPosition(x, y);
	}

	public void setDead(boolean dead) {
		this.dead = dead;
	}

	public boolean isDead() {
		return dead;
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}
}
