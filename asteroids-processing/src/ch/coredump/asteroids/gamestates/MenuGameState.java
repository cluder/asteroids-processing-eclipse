package ch.coredump.asteroids.gamestates;

import ch.coredump.asteroids.GameManager;
import ch.coredump.asteroids.ui.Button;
import processing.core.PApplet;
import processing.core.PConstants;

/**
 * Main menu game state.<br>
 * Welcome screen and ability to start the game.
 */
public class MenuGameState extends BaseGameState {
	Button play;

	public MenuGameState(PApplet p, GameManager manager) {
		super(p, manager);
	}

	@Override
	public void init() {
		play = new Button(this, p.width / 2, p.height * 0.6f, 150, 80, "Play");
	}

	@Override
	protected void doUpdate(long tpf) {
		play.update();
	}

	@Override
	public void doRender() {
		p.background(50);

		p.fill(255);
		p.textMode(PConstants.SHAPE);

		p.textSize(50);
		p.textAlign(PConstants.CENTER, PConstants.CENTER);
		p.text("A S T E R O I D S", p.width / 2, p.height * 0.2f);

		p.textSize(20);
		p.text("Click PLAY to start", p.width / 2, p.height * 0.4f);

		// play button
		play.render();
	}

	public GameManager getManager() {
		return manager;
	}
}
