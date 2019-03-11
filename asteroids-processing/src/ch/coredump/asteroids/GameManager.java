package ch.coredump.asteroids;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import ch.coredump.asteroids.entities.Asteroid;
import ch.coredump.asteroids.entities.BaseEntity;
import ch.coredump.asteroids.entities.BoundingBox;
import ch.coredump.asteroids.entities.Projectile;
import ch.coredump.asteroids.entities.Spaceship;
import processing.core.PApplet;

/**
 * Game manager responsible for updating / rendering all game entities.
 */
public class GameManager {
	private List<Asteroid> asteroids = new ArrayList<>();
	private List<Projectile> projectiles = new ArrayList<>();
	private int width;
	private int height;

	int numHits = 0;

	private Spaceship spaceship;

	public GameManager(int width, int heigth) {
		this.width = width;
		this.height = heigth;
	}

	void addAsteroid(Asteroid e) {
		asteroids.add(e);
	}

	public void addProjectile(Projectile p) {
		projectiles.add(p);
	}

	void addPlayer(Spaceship spaceship) {
		this.spaceship = spaceship;
	}

	/**
	 * Updates/moves the game entities
	 */
	void update(float fps) {
		// time per frame
		float tpf = 60 / fps;

		// temporary list
		List<BaseEntity> deadProjectiles = new ArrayList<>();
		List<BaseEntity> deadAsteroids = new ArrayList<>();

		// update projectiles
		for (Projectile e : projectiles) {
			e.update(tpf);
			Projectile p = (Projectile) e;
			if (p.isDead()) {
				deadProjectiles.add(e);
			}

			// check for hits (projectile vs asteroid)
			for (Asteroid a : asteroids) {
				final BoundingBox bbProjectile = p.getBoundingBox();
				final BoundingBox bbAsteroid = a.getBoundingBox();
				if (bbProjectile.intersects(bbAsteroid)) {
					// asteroid hit - remove projectile and asteroid
					deadProjectiles.add(e);
					deadAsteroids.add(a);
				}
			}
		}

		int numAsteroidsHit = deadAsteroids.size();
		numHits += numAsteroidsHit;

		// remove dead entities
		projectiles.removeAll(deadProjectiles);
		asteroids.removeAll(deadAsteroids);

		// update other entities
		for (BaseEntity e : asteroids) {
			e.update(tpf);
		}
		for (BaseEntity e : projectiles) {
			e.update(tpf);
		}

		spaceship.update(tpf);

		// spawn new asteroids
		spawnAsteroids(numAsteroidsHit);
	}

	/**
	 * Draws the game entities.
	 */
	void drawAll(PApplet processing) {
		spaceship.render(processing);

		for (BaseEntity e : asteroids) {
			e.render(processing);
		}
		for (BaseEntity e : projectiles) {
			e.render(processing);
		}

		processing.textSize(20);
		processing.text("Hits: " + numHits, 20, 30);
	}

	/**
	 * Create asteroids in random locations.
	 */
	public void spawnAsteroids(int num) {
		Random r = new Random();
		for (int i = 0; i < num; i++) {
			Asteroid a = new Asteroid(r.nextInt(width), r.nextInt(height));
			addAsteroid(a);
		}
	}

	public int getHeight() {
		return height;
	}

	public int getWidth() {
		return width;
	}

}
