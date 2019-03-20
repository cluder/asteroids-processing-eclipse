package ch.coredump.asteroids;

import ch.coredump.asteroids.gamestates.BaseGameState;
import processing.core.PApplet;
import processing.event.KeyEvent;
import processing.event.MouseEvent;

/**
 * Game manager responsible for updating / rendering all game entities.
 */
public class GameManager {
	BaseGameState currentGameState;

	public GameManager() {
	}

	/**
	 * Updates the currently active game state.
	 */
	void update() {
		currentGameState.update();
	}

	/**
	 * Renders the currently active game state.
	 */
	void render(PApplet p) {
		currentGameState.render();
	}

	public void mousePressed(MouseEvent event) {
		currentGameState.mousePressed(event);
	}

	public void keyPressed(KeyEvent event) {
		currentGameState.keyPressed(event);
	}

	public void keyReleased(KeyEvent event) {
		currentGameState.keyReleased(event);
	}

	/**
	 * Sets the currently active game state.
	 */
	public void setGameState(BaseGameState state) {
		currentGameState = state;
	}

}
