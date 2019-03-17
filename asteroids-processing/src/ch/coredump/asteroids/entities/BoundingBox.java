package ch.coredump.asteroids.entities;

import processing.core.PApplet;

/**
 * A simple rect for collision detection.
 */
public class BoundingBox {
	// absolute coords
	float x = 0;
	float y = 0;
	float w = 0;
	float h = 0;

	public BoundingBox() {
	}

	public void setSize(float w, float h) {
		this.w = w;
		this.h = h;

		// adapt position
		this.x -= w / 2;
		this.y -= h / 2;
	}

	public void setPosition(float x, float y) {
		this.x = x;
		this.y = y;

		this.x -= w / 2;
		this.y -= h / 2;
	}

	/**
	 * Checks if the two rects overlap.<br>
	 * Does not take rotation into consideration.
	 * 
	 * @return true if one rect overlaps the other.
	 */
	public boolean intersects(BoundingBox bbOther) {

		// check if the rects don't overlap
		if (x + w < bbOther.x) {
			// a is left from b
			return false;
		}
		if (y + h < bbOther.y) {
			// a is top of b
			return false;
		}

		if (x > bbOther.x + bbOther.w) {
			// a is to the right of b
			return false;
		}

		if (y > bbOther.y + bbOther.h) {
			// a is below b
			return false;
		}

		return true;
	}

	public void render(PApplet p) {
		p.push();

		p.noFill();
		p.stroke(50);
		p.translate(x, y);
		p.rect(0, 0, w, h);

		p.pop();

	}

}
