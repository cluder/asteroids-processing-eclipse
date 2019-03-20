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
		// start window on 2nd monitor (-1900)
		// --display does not seem to work
		String[] newArgs = append(args, "--location=-1900,400");
		newArgs = append(newArgs, AsteroidsMain.class.getName());
		PApplet.main(newArgs);
	}

	/**
	 * Set screen size.
	 */
	@Override
	public void settings() {
//		size(800, 600);
		size(800, 600, P2D);
	}

	/**
	 * Set the background color, frame rate.<br>
	 * Called once at the start.
	 */
	@Override
	public void setup() {

//		noCursor();
		cursor(CROSS);
		manager = new GameManager(this, width, height);

		// create asteroids and player
		manager.spawnAsteroids(15);

		player = new Spaceship(width / 4, height / 2);
		player.setRotation(90);
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

		manager.update();
		manager.render(this);
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
			player.moveForward(pressed);
		}
		if (keyCode == LEFT || key == 'a') {
			player.rotateLeft(pressed);
		}
		if (keyCode == RIGHT || key == 'd') {
			player.rotateRight(pressed);
		}

		if (keyCode == BACKSPACE && pressed) {
			manager.resetPlayer();
		}
		if (key == ' ' && pressed) {
			fire();
		}

		if (key == 'r' && pressed) {
			manager.resetStarfield();
		}
	}

	@Override
	public void mousePressed() {
		fire();
	}

	private void fire() {
		if (player.isDead()) {
			return;
		}
		manager.addProjectile(player.fire());
	}
}
