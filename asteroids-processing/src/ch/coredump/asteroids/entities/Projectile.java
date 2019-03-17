package ch.coredump.asteroids.entities;

import processing.core.PApplet;
import processing.core.PVector;

public class Projectile extends BaseEntity {
	float speed = 0.2f;
	float size = 5;

	// time to live in ms
	final long timeToLive = 1000;
	final long created;

	public Projectile(float x, float y, float rotation) {
		super(x, y);
		setRotation(rotation);

		created = System.currentTimeMillis();
		setBoundingBoxSize(size, size);
	}

	@Override
	public void update(float tpf) {
		if (dead) {
			return;
		}

		// we subtract 90°, so that 0° points north
		final double rotationRadians = Math.toRadians(getRotation() - 90);
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

		// check if time to live is reached
		if (created + timeToLive < System.currentTimeMillis()) {
			dead = true;
		}
	}

	@Override
	public void render(PApplet p) {
		if (dead) {
			return;
		}
		super.render(p);

		p.push();
		p.stroke(255);
		p.translate(getX(), getY());
		// create a line from the spaceship's tip
		p.rotate(PApplet.radians(getRotation()));
		p.line(0, -size, 0, 0);
		p.pop();
	}

}
