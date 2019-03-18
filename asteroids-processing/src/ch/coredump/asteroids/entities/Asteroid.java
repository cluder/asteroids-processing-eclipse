package ch.coredump.asteroids.entities;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PGraphics;
import processing.core.PShape;

public class Asteroid extends BaseEntity {
	float size;
	PGraphics gfx;
	PShape shape;
	float rotationSpeed = 0.01f;

	public Asteroid(PApplet p, float x, float y) {
		super(x, y);

		float rotVar = 0.1f;
		rotationSpeed += ((float) Math.random() * rotVar) - rotVar / 2;

		float sizeVar = 15;
		size = 30;
		size += ((float) Math.random() * sizeVar) - sizeVar / 2;
		setBoundingBoxSize(size, size);

		gfx = p.createGraphics((int) size, (int) size);
		init(p);
	}

	private void init(PApplet p) {
		// create a custom shape
		shape = gfx.createShape();
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
			shape.vertex(vx, vy, 0);
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
		setRotation(getRotation() + rotationSpeed * tpf);
	}
}
