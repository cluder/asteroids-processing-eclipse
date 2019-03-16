package ch.coredump.asteroids;

import ch.coredump.asteroids.entities.Projectile;
import ch.coredump.asteroids.entities.Spaceship;
import processing.core.PApplet;
import processing.event.KeyEvent;

/**
 * Main class, setup and start the processing environment.
 */
public class AsteroidsMain extends PApplet {
	GameManager manager;
	Spaceship player;

	public static void main(String[] args) {
		PApplet.main(AsteroidsMain.class, args);
	}

	public AsteroidsMain() {
		manager = new GameManager(800, 600);

		// create one asteroid and player
		manager.spawnAsteroids(5);

		player = new Spaceship(manager.getWidth() / 2, manager.getHeight() / 2);
		manager.addPlayer(player);

	}

	/**
	 * Set the background color, frame rate.<br>
	 * Called once at the start.
	 */
	@Override
	public void setup() {
		System.out.println("setup()");
		super.setup();

		// clear screen
		background(0);

		// framerate 30
		frameRate(30);
	}

	/**
	 * Set screen size.
	 */
	@Override
	public void settings() {
		System.out.println("settings()");
		super.settings();
		size(manager.getWidth(), manager.getHeight());
	}

	/**
	 * Main game loop.<br>
	 * Called continuously depending on the frame rate.
	 */
	@Override
	public void draw() {
		background(20);

		manager.update(frameRate);
		manager.drawAll(this);
	}

	@Override
	public synchronized void redraw() {
		System.out.println("redraw()");
		super.redraw();
	}

	@Override
	public void keyPressed() {
		super.keyPressed();
		movementKey(key, keyCode, true);
	}

	@Override
	public void keyReleased(KeyEvent event) {
		System.out.println("keyReleased()");
		super.keyReleased(event);

		movementKey(key, keyCode, false);
	}

	private void movementKey(char key, int keyCode, boolean pressed) {
		if (keyCode == UP || key == 'w') {
			player.moveUp(pressed);
		}
		if (keyCode == LEFT || key == 'a') {
			player.rotateLeft(pressed);
		}
		if (keyCode == RIGHT || key == 'd') {
			player.rotateRight(pressed);
		}

		if (keyCode == BACKSPACE) {
			reset();
		}
	}

	private void reset() {
		player.setPosition(300, 300);
	}

	@Override
	public void mousePressed() {
		System.out.println("mousePressed()");
		super.mousePressed();

		Projectile p = new Projectile(player.getX(), player.getY(), player.getRotation());
		manager.addProjectile(p);
	}
}
