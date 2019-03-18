package ch.coredump.asteroids;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import ch.coredump.asteroids.effects.ParallaxStarField;
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
	private ParallaxStarField starField;
	private List<Asteroid> asteroids = new ArrayList<>();
	private List<Projectile> projectiles = new ArrayList<>();
	private int width;
	private int height;
	private PApplet processing;

	int numHits = 0;

	private Spaceship spaceship;

	long lastFrameTime = System.currentTimeMillis();
	long tpf = 0;

	public GameManager(PApplet p, int width, int heigth, int numStars, int starLayers) {
		this.width = width;
		this.height = heigth;
		this.processing = p;

		starField = new ParallaxStarField(this, numStars, starLayers, width, heigth);
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
	void update() {

		final long currentTime = System.currentTimeMillis();
		tpf = currentTime - lastFrameTime;
		lastFrameTime = currentTime;

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

		// player vs asteroid
		for (Asteroid a : asteroids) {
			// asteroids outside of the screen are deleted
			if (a.getX() + a.getBoundingBox().getW() < 0) {
				a.setDead(true);
				deadAsteroids.add(a);
			}
			if (spaceship.getBoundingBox().intersects(a.getBoundingBox())) {
				spaceship.setDead(true);
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

		starField.update(tpf);
	}

	/**
	 * Draws the game entities.
	 */
	void render(PApplet p) {
		spaceship.render(p);

		for (BaseEntity e : asteroids) {
			e.render(p);
		}
		for (BaseEntity e : projectiles) {
			e.render(p);
		}

		int fpsFrameTime = Math.round(1000.f / tpf);

		p.textSize(15);
		p.text("FPS (internal): " + Math.round(p.frameRate) + " frametime:" + fpsFrameTime, 20, 20);

		p.textSize(20);
		p.text("Hits: " + numHits, 20, 60);

		starField.render(p);
	}

	/**
	 * Create asteroids in random locations.<br>
	 * Asteroids are spawned outside of the screen, to let them fly in from the
	 * right.
	 */
	public void spawnAsteroids(int num) {
		Random r = new Random();
		for (int i = 0; i < num; i++) {
			Asteroid a = new Asteroid(processing, r.nextInt(width) + width, r.nextInt(height));
			addAsteroid(a);
		}
	}

	public int getHeight() {
		return height;
	}

	public int getWidth() {
		return width;
	}

	public PApplet getProcessing() {
		return processing;
	}

	public Spaceship getSpaceship() {
		return spaceship;
	}

	public void resetStarfield(int layers, int stars) {
		starField.reset(layers, stars);
	}
}
