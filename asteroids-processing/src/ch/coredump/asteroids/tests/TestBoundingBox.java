package ch.coredump.asteroids.tests;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import ch.coredump.asteroids.entities.BoundingBox;

class TestBoundingBox {

	/**
	 * Check intersection of two Bounding Boxes with a width / height of 50.
	 */
	@Test
	void testIntersect() {
		BoundingBox a = new BoundingBox();
		BoundingBox b = new BoundingBox();

		a.setSize(50, 50);
		b.setSize(50, 50);

		// rects intersect
		a.setPosition(10, 10);
		b.setPosition(30, 30);

		assertTrue(a.intersects(b));
	}

	@Test
	void testNoIntersect() {
		BoundingBox a = new BoundingBox();
		BoundingBox b = new BoundingBox();

		a.setSize(50, 50);
		b.setSize(50, 50);

		// b is left of a
		a.setPosition(10, 10);
		b.setPosition(70, 30);
		assertTrue(a.intersects(b) == false);

		// b is right of a
		a.setPosition(70, 10);
		b.setPosition(10, 30);
		assertTrue(a.intersects(b) == false);
	}

}
