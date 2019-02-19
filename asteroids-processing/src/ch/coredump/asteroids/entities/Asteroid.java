package ch.coredump.asteroids.entities;

import processing.core.PApplet;

public class Asteroid extends BaseEntity {
	float size = 40;

	public Asteroid(float x, float y) {
		super(x, y);
	}

	@Override
	public void render(PApplet processing) {
		processing.noFill();
		processing.stroke(255);
		processing.circle(getX(), getY(), size);
	}

	@Override
	public void update(float tpf) {

	}
}
