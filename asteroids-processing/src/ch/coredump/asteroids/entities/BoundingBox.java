package ch.coredump.asteroids.entities;

/**
 * A simple rect for collision detection.
 */
public class BoundingBox {
	float x = 0;
	float y = 0;
	float w = 0;
	float h = 0;

	public BoundingBox() {
	}

	public void setSize(float w, float h) {
		this.w = w;
		this.h = h;
	}

	public void setPosition(float x, float y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Checks if the two rects overlap.
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
}
