package ch.coredump.asteroids.entities;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PShape;

public class Asteroid extends BaseEntity {
	float size;
	PShape shape;
	float rotationSpeed = 0.01f;
	float speedX = -0.1f;
	float speedY = 0f;

	public Asteroid(PApplet p, float x, float y) {
		super(x, y);

		float rotVar = 0.1f;
		rotationSpeed += ((float) Math.random() * rotVar) - rotVar / 2;

		// speed variance
		speedX *= Math.random() * 3;
		speedY = (float) (Math.random() * 0.05f);

		float sizeVar = 15;
		size = 30;
		size += ((float) Math.random() * sizeVar) - sizeVar / 2;
		setBoundingBoxSize(size, size);

		initShape(p);
	}

	private void initShape(PApplet p) {
		// create a custom shape
		shape = p.createShape(PShape.GEOMETRY);
		shape.beginShape();
		shape.stroke(255);
		shape.strokeWeight(2);
		shape.fill(155);

		float radius = size / 2;
		float maxVar = 12;
		float vCount = 6;
		for (int i = 0; i < vCount; i++) {
			float var = (float) Math.random() * maxVar;
			var -= maxVar / 2;
			float percentDone = (i / (float) (vCount));
			float rad = (float) (percentDone * 2 * Math.PI);
			float vx = (radius + var) * (float) Math.cos(rad);
			float vy = (radius + var) * (float) Math.sin(rad);
			shape.vertex(vx, vy);
		}
		shape.endShape(PConstants.CLOSE);
	}

	@Override
	public void render(PApplet p) {
		super.render(p);

		p.push();

		p.translate(getX(), getY());
		p.rotate(PApplet.radians(getRotation()));
		p.shape(shape);

		p.pop();
	}

	@Override
	public void update(float tpf) {

		float newX = getX() + speedX * tpf;
		float newY = getY() + speedY * tpf;
		setPosition(newX, newY);

		setRotation(getRotation() + rotationSpeed * tpf);
	}
}
