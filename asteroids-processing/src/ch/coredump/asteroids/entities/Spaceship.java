package ch.coredump.asteroids.entities;

import processing.core.PApplet;
import processing.core.PVector;

public class Spaceship extends BaseEntity {
	boolean moveForward = false;
	boolean rotateLeft = false;
	boolean rotateRight = false;

	int size = 20;

	// rotation speed
	float rotationSpeed = 0.1f;

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
	public void render(PApplet p) {
		if (dead) {
			return;
		}
		super.render(p);

		p.push();

		p.noFill();
		p.stroke(255);

		final float x1 = 0;
		final float y1 = -size / 2;

		final float x2 = size / 2;
		final float y2 = size / 2;

		final float x3 = -size / 2;
		final float y3 = size / 2;

		p.translate(getX(), getY());
		p.rotate(PApplet.radians(getRotation()));
		p.triangle(x1, y1, x2, y2, x3, y3);
		p.pop();
	}

	@Override
	public void update(float tpf) {
		if (dead) {
			return;
		}
		final float rotationSpeedPerFrame = rotationSpeed * tpf;

		if (moveForward) {
			// we subtract 90°, so that 0° points north
			final double rotationRadians = Math.toRadians(getRotation() - 90);
			float dirX = (float) Math.cos(rotationRadians);
			float dirY = (float) Math.sin(rotationRadians);
			PVector direction = new PVector(dirX, dirY);

			direction = direction.normalize();

			// then scale it by the current speed to get the velocity
			float velX = direction.x * speed;
			float velY = direction.y * speed;

			float newX = getX() + velX * tpf;
			float newY = getY() + velY * tpf;

			setPosition(newX, newY);
		}

		if (rotateLeft) {
			setRotation(getRotation() - rotationSpeedPerFrame);
		}

		if (rotateRight) {
			setRotation(getRotation() + rotationSpeedPerFrame);
		}

		setRotation(getRotation() % 360);
	}

	public void moveForward(boolean value) {
		moveForward = value;
	}

	public void rotateLeft(boolean value) {
		rotateLeft = value;
	}

	public void rotateRight(boolean value) {
		rotateRight = value;
	}

	public Projectile fire() {
		return new Projectile(getX(), getY(), getRotation());
	}

	public void setY(float newY) {
		setPosition(getX(), newY);
	}

	public void setX(float newX) {
		setPosition(newX, getY());
	}
}
