package ch.coredump.asteroids.effects;

import processing.core.PVector;

public class Star {
	PVector v = new PVector();
	float xOffset;
	float yOffset;

	public Star(float x, float y) {
		v.x = x;
		v.y = y;
	}

	public float getX() {
		return v.x;
	}

	public void setX(float x) {
		v.x = x;
	}

	public float getY() {
		return v.y;
	}

	public void setY(float y) {
		v.y = y;
	}

	public void setXOffset(float xOffset) {
		this.xOffset = xOffset;
	}

	public void setYOffset(float yOffset) {
		this.yOffset = yOffset;
	}

	public float getXOffset() {
		return xOffset;
	}

	public float getYOffset() {
		return yOffset;
	}
}
