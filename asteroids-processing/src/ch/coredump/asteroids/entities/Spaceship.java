package ch.coredump.asteroids.entities;

import processing.core.PApplet;
import processing.core.PVector;

public class Spaceship extends BaseEntity {
	boolean moveUp = false;
	boolean rotateLeft = false;
	boolean rotateRight = false;

	// 50 pixel size
	int size = 20;

	// rotation speed
	float rotationSpeed = 2f;

	// current rotation in °, 0 points north
	float currentRotation = -0;

	public Spaceship(float x, float y) {
		super(x, y);
		setBoundingBoxSize(size, size);
	}

	/**
	 * Renders as a triangle.<br>
	 * p1 = middle top<br>
	 * p2 = bottom right<br>
	 * p2 = bottom left<br>
	 */
	@Override
	public void render(PApplet processing) {
		processing.noFill();
		processing.stroke(255);

		final float x1 = 0;
		final float y1 = -size / 2;

		final float x2 = size / 2;
		final float y2 = size / 2;

		final float x3 = -size / 2;
		final float y3 = size / 2;

		processing.pushMatrix();
		{
			processing.translate(getX(), getY());
			processing.rotate(PApplet.radians(currentRotation));
			processing.triangle(x1, y1, x2, y2, x3, y3);
		}
		processing.popMatrix();
	}

	@Override
	public void update(float tpf) {
		final float rotationSpeedPerFrame = rotationSpeed * tpf;

		if (moveUp) {
			// we subtract 90°, so that 0° points north
			final double rotationRadians = Math.toRadians(currentRotation - 90);
			float dirX = (float) Math.cos(rotationRadians);
			float dirY = (float) Math.sin(rotationRadians);
			PVector direction = new PVector(dirX, dirY);

			direction = direction.normalize();

			// Then scale it by the current speed to get the velocity
			float velX = direction.x * speed;
			float velY = direction.y * speed;

			float newX = getX() + velX * tpf;
			float newY = getY() + velY * tpf;

			setPosition(newX, newY);

		}

		if (rotateLeft) {
			currentRotation -= rotationSpeedPerFrame;
		}

		if (rotateRight) {
			currentRotation += rotationSpeedPerFrame;
		}

		currentRotation %= 360;
	}

	public void moveUp(boolean value) {
		moveUp = value;
	}

	public void rotateLeft(boolean value) {
		rotateLeft = value;
	}

	public void rotateRight(boolean value) {
		rotateRight = value;
	}

	public float getRotation() {
		return currentRotation;
	}

}
