package ch.coredump.asteroids.entities;

import processing.core.PApplet;

public abstract class BaseEntity {
	private float rotation = 0;
	private float x = 0;
	private float y = 0;
	protected float speed = 0.1f;
	BoundingBox bb;
	boolean dead = false;

	boolean drawBoundingBox = false;

	public BaseEntity(float x, float y) {
		this.x = x;
		this.y = y;

		// default bounding box (size = 0)
		bb = new BoundingBox();
		bb.setPosition(x, y);
	}

	protected void setBoundingBoxSize(float w, float h) {
		bb.setSize(w, h);
	}

	protected void setBoundingBoxSize(float size) {
		bb.setSize(size, size);
	}

	public BoundingBox getBoundingBox() {
		return bb;
	}

	public float getRotation() {
		return rotation;
	}

	public void setRotation(float rotation) {
		this.rotation = rotation;
	}

	public void setDrawBoundingBox(boolean drawBoundingBox) {
		this.drawBoundingBox = drawBoundingBox;
	}

	/**
	 * draws the bounding box if enabled.
	 */
	public void render(PApplet processing) {
		if (drawBoundingBox) {
			bb.render(processing);
		}
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
