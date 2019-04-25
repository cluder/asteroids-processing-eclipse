package ch.coredump.asteroids;

import ch.coredump.asteroids.entities.Spaceship;
import ch.coredump.asteroids.gamestates.MenuGameState;
import processing.core.PApplet;
import processing.event.KeyEvent;
import processing.event.MouseEvent;

/**
 * Main class, setup and start the processing environment.
 */
public class AsteroidsMain extends PApplet {

	GameManager manager;
	Spaceship player;

	public static void main(String[] args) {
		// start window on 2nd monitor (-1900)
		// --display does not seem to work
		String[] newArgs = new String[0];
//		String[] newArgs = append(args, "--location=-1900,400");
		newArgs = append(newArgs, AsteroidsMain.class.getName());
		PApplet.main(newArgs);
	}

	/**
	 * Set screen size.
	 */
	@Override
	public void settings() {
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

		// create game manager with an initial game state (menu)
		manager = new GameManager();
		final MenuGameState menuGameState = new MenuGameState(this, manager);
		manager.setGameState(menuGameState);

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
	public void keyPressed(KeyEvent event) {
		manager.keyPressed(event);
	}

	@Override
	public void keyReleased(KeyEvent event) {
		manager.keyReleased(event);
	}

	/**
	 * Pass event to GameManager, which passes it to the current active GameState.
	 */
	@Override
	public void mousePressed(MouseEvent event) {
		manager.mousePressed(event);
	}
}
