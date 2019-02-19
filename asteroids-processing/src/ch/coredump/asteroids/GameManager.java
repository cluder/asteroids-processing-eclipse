package ch.coredump.asteroids;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import ch.coredump.asteroids.entities.Asteroid;
import ch.coredump.asteroids.entities.BaseEntity;
import ch.coredump.asteroids.entities.Projectile;
import processing.core.PApplet;

/**
 * Game manager responsible for updating / rendering all game entities.
 */
public class GameManager {
	private List<BaseEntity> entities = new ArrayList<>();
	private List<Projectile> projectiles = new ArrayList<>();
	private int width;
	private int height;

	public GameManager(int width, int heigth) {
		this.width = width;
		this.height = heigth;
	}

	void addEntity(BaseEntity e) {
		entities.add(e);
	}

	/**
	 * Updates/moves the game entities
	 */
	void update(float fps) {
		// time per frame
		float tpf = 60 / fps;

		// temporary list
		List<Projectile> deadEntities = new ArrayList<>();

		// update projectiles
		for (Projectile e : projectiles) {
			e.update(tpf);
			Projectile p = (Projectile) e;
			if (p.isDead()) {
				deadEntities.add(e);
			}
			// TOTO check projectile intersecting asteroid
		}

		// remove dead projectiles
		projectiles.removeAll(deadEntities);

		// update other entities
		for (BaseEntity e : entities) {
			e.update(tpf);
		}

	}

	/**
	 * Draws the game entities.
	 */
	void drawAll(PApplet processing) {
		for (BaseEntity e : entities) {
			e.render(processing);
		}
	}

	/**
	 * Create asteroids in random locations.
	 */
	public void spawnAsteroids(int num) {
		Random r = new Random();
		for (int i = 0; i < num; i++) {
			Asteroid a = new Asteroid(r.nextInt(width), r.nextInt(height));
			addEntity(a);
		}

	}

	public int getHeight() {
		return height;
	}

	public int getWidth() {
		return width;
	}
}
