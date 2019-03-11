package ch.coredump.asteroids.entities;

import processing.core.PApplet;
import processing.core.PVector;

public class Projectile extends BaseEntity {
	float rotation;
	float speed = 5;
	float size = 10;

	// particle can live 2 seconds
	final long timeToLive = 2000;
	final long created;

	public Projectile(float x, float y, float rotation) {
		super(x, y);
		this.rotation = rotation;

		created = System.currentTimeMillis();
		setBoundingBoxSize(size, size);
	}

	@Override
	public void update(float tpf) {
		if (dead) {
			return;
		}
		// we subtract 90°, so that 0° points north
		final double rotationRadians = Math.toRadians(rotation - 90);
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
	public void render(PApplet processing) {
		if (dead) {
			return;
		}
		processing.pushMatrix();
		{
			processing.translate(getX(), getY());
			processing.rotate(PApplet.radians(rotation));
			// create a line from the spaceship's tip
			processing.line(0, -size, 0, 0);
		}
		processing.popMatrix();
	}

}
