package ch.coredump.asteroids.gamestates;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import ch.coredump.asteroids.GameManager;
import ch.coredump.asteroids.effects.StarField;
import ch.coredump.asteroids.entities.Asteroid;
import ch.coredump.asteroids.entities.BaseEntity;
import ch.coredump.asteroids.entities.BoundingBox;
import ch.coredump.asteroids.entities.Projectile;
import ch.coredump.asteroids.entities.Spaceship;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.event.KeyEvent;
import processing.event.MouseEvent;

/**
 * Game state containing the main game.
 */
public class MainGameState extends BaseGameState {
	private StarField starField;
	private Spaceship player;

	private int score = 0;
	private List<Asteroid> asteroids;
	private List<Projectile> projectiles;

	public MainGameState(PApplet p, GameManager manager) {
		super(p, manager);
	}

	@Override
	public void init() {
		super.init();

		asteroids = new ArrayList<>();
		projectiles = new ArrayList<>();
		starField = new StarField(this, p.width, p.height);

		// create asteroids and player
		spawnAsteroids(15);

		player = new Spaceship(p.width / 4, p.height / 2);
		player.setRotation(90);
		addPlayer(player);
	}

	@Override
	protected void doUpdate(long tpf) {
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
			if (player.getBoundingBox().intersects(a.getBoundingBox())) {
				player.setDead(true);
			}
		}

		removeDeadEntities();

		// spawn new asteroids
		spawnAsteroids(asteroidsHit);

	}

	@Override
	protected void doRender() {

		player.render(p);

		for (BaseEntity e : asteroids) {
			e.render(p);
		}
		for (BaseEntity e : projectiles) {
			e.render(p);
		}

		p.fill(255);
		p.textAlign(PConstants.BASELINE);
		p.textSize(15);
		p.text(String.format("FPS: %.1f", p.frameRate), 20, 20);
//		float fps = (float) (1000.0 / tpf);
//		p.text(String.format("FPS: %.1f (%.1f) ", p.frameRate, fps), 20, 20);

		p.textSize(20);
		p.text("Score: " + score, 20, 60);

		starField.render(p);
	}

	@Override
	public void mousePressed(MouseEvent event) {
		super.mousePressed(event);

		fire();
	}

	@Override
	public void keyPressed(KeyEvent event) {
		super.keyPressed(event);

		movementKey(event.getKey(), event.getKeyCode(), true);
	}

	@Override
	public void keyReleased(KeyEvent event) {
		super.keyReleased(event);

		movementKey(event.getKey(), event.getKeyCode(), false);
	}

	private void movementKey(char key, int keyCode, boolean pressed) {
		if (keyCode == PApplet.UP || key == 'w') {
			player.moveForward(pressed);
		}
		if (keyCode == PApplet.LEFT || key == 'a') {
			player.rotateLeft(pressed);
		}
		if (keyCode == PApplet.RIGHT || key == 'd') {
			player.rotateRight(pressed);
		}

		if (keyCode == PApplet.BACKSPACE && pressed) {
			resetPlayer();
		}
		if (key == ' ' && pressed) {
			fire();
		}

		if (key == 'r' && pressed) {
			resetStarfield();
		}
	}

	private void fire() {
		if (player.isDead()) {
			return;
		}
		addProjectile(player.fire());
	}

	private void removeDeadEntities() {
		projectiles.removeIf(x -> x.isDead());
		asteroids.removeIf(x -> x.isDead());
	}

	private void updatePlayer() {
		if (player.isDead()) {

			// test
			manager.setGameState(new MenuGameState(p, manager));

			return;
		}
		player.update(tpf);
		if (player.getY() < 0) {
			player.setY(player.getY() + p.height);
		}
		if (player.getY() > p.height) {
			player.setY(player.getY() - p.height);
		}

		player.setX(player.getX() % p.width);
		if (player.getX() < 0) {
			player.setDead(true);
		}

		// test
		float drag = 0.05f;
		player.setX(player.getX() - drag * tpf);
	}

	/**
	 * Create asteroids in random locations.<br>
	 * Asteroids are spawned outside of the screen, to let them fly in from the
	 * right.
	 */
	public void spawnAsteroids(int num) {
		Random r = new Random();
		for (int i = 0; i < num; i++) {
			Asteroid a = new Asteroid(p, r.nextInt(p.width) + p.width, r.nextInt(p.height));
			addAsteroid(a);
		}
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

	public Spaceship getSpaceship() {
		return player;
	}

	void addAsteroid(Asteroid e) {
		asteroids.add(e);
	}

	public void addProjectile(Projectile p) {
		projectiles.add(p);
	}

	void addPlayer(Spaceship spaceship) {
		this.player = spaceship;
	}

	public void resetPlayer() {
		player.setDead(false);
		player.setPosition(p.width / 4, p.height / 2);
		player.setRotation(90);
		setScore(0);
	}
}
