package ch.coredump.asteroids.gamestates;

import ch.coredump.asteroids.GameManager;
import processing.core.PApplet;
import processing.event.KeyEvent;
import processing.event.MouseEvent;

/**
 * Base class for all GameStates.<br>
 * Contains basic logic used in all parts of the game.
 */
public abstract class BaseGameState {
	GameManager manager;
	PApplet p;

	long lastFrameTime = System.currentTimeMillis();
	long tpf = 0;

	public BaseGameState(PApplet p, GameManager manager) {
		this.p = p;
		this.manager = manager;

		init();
	}

	public void init() {

	}

	public void render() {
		doRender();
	}

	protected abstract void doRender();

	public void update() {
		final long currentTime = System.currentTimeMillis();
		tpf = currentTime - lastFrameTime;
		lastFrameTime = currentTime;

		doUpdate(tpf);
	}

	protected abstract void doUpdate(long tpf);

	public void mousePressed(MouseEvent event) {

	}

	public void keyPressed(KeyEvent event) {

	}

	public void keyReleased(KeyEvent event) {

	}

	public PApplet getProcessing() {
		return p;
	}
}
