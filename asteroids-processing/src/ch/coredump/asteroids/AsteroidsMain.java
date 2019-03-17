package ch.coredump.asteroids;

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

	}

	/**
	 * Set screen size.
	 */
	@Override
	public void settings() {
		size(800, 600);
	}

	/**
	 * Set the background color, frame rate.<br>
	 * Called once at the start.
	 */
	@Override
	public void setup() {
		noCursor();
		manager = new GameManager(this, width, height);

		// create one asteroid and player
		manager.spawnAsteroids(10);

		player = new Spaceship(manager.getWidth() / 2, manager.getHeight() / 2);
		manager.addPlayer(player);

		// target framerate 30 fps
		frameRate(30);
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
	public void keyPressed() {
		movementKey(key, keyCode, true);
	}

	@Override
	public void keyReleased(KeyEvent event) {
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
		if (key == ' ' && pressed) {
			fire();
		}

	}

	private void reset() {
		player.setPosition(300, 300);
	}

	@Override
	public void mousePressed() {
		fire();
	}

	private void fire() {
		manager.addProjectile(player.fire());
	}
}
