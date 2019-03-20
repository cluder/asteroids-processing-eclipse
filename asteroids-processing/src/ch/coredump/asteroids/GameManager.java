package ch.coredump.asteroids;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import ch.coredump.asteroids.effects.StarField;
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
	private StarField starField;
	private List<Asteroid> asteroids = new ArrayList<>();
	private List<Projectile> projectiles = new ArrayList<>();
	private int width;
	private int height;
	private PApplet processing;

	private int score = 0;

	private Spaceship spaceship;

	long lastFrameTime = System.currentTimeMillis();
	long tpf = 0;

	public GameManager(PApplet p, int width, int heigth) {
		this.width = width;
		this.height = heigth;
		this.processing = p;

		starField = new StarField(this, width, heigth);
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
		int asteroidsHit = 0;

		starField.update(tpf);
		// update other entities
		for (BaseEntity e : asteroids) {
			e.update(tpf);
		}
		for (BaseEntity e : projectiles) {
			e.update(tpf);
		}

		updatePlayer();

		// update projectiles
		for (Projectile e : projectiles) {
			e.update(tpf);
			Projectile p = (Projectile) e;

			// check for hits (projectile vs asteroid)
			for (Asteroid a : asteroids) {
				final BoundingBox bbProjectile = p.getBoundingBox();
				final BoundingBox bbAsteroid = a.getBoundingBox();
				if (bbProjectile.intersects(bbAsteroid)) {
					// asteroid hit - remove projectile and asteroid
					e.setDead(true);
					a.setDead(true);
					asteroidsHit++;
					score++;
				}
			}
		}

		// player vs asteroid
		for (Asteroid a : asteroids) {
			// asteroids outside of the screen are deleted
			if (a.getX() + a.getBoundingBox().getW() < 0) {
				a.setDead(true);
				asteroidsHit++;
			}
			if (spaceship.getBoundingBox().intersects(a.getBoundingBox())) {
				spaceship.setDead(true);
			}
		}

		removeDeadEntities();

		// spawn new asteroids
		spawnAsteroids(asteroidsHit);
	}

	private void removeDeadEntities() {
		projectiles.removeIf(x -> x.isDead());
		asteroids.removeIf(x -> x.isDead());
	}

	private void updatePlayer() {
		spaceship.update(tpf);
		if (spaceship.getY() < 0) {
			spaceship.setY(spaceship.getY() + height);
		}
		if (spaceship.getY() > height) {
			spaceship.setY(spaceship.getY() - height);
		}

		spaceship.setX(spaceship.getX() % width);
		if (spaceship.getX() < 0) {
			spaceship.setDead(true);
		}
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

		p.textSize(15);
		p.text(String.format("FPS: %.1f", p.frameRate), 20, 20);
//		float fps = (float) (1000.0 / tpf);
//		p.text(String.format("FPS: %.1f (%.1f) ", p.frameRate, fps), 20, 20);

		p.textSize(20);
		p.text("Score: " + score, 20, 60);

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

	/**
	 * mainly for testing.
	 */
	public void resetStarfield() {
		starField.init();
	}

	public void setScore(int score) {
		this.score = score;
	}
}
