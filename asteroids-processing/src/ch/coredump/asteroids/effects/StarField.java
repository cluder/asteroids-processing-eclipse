package ch.coredump.asteroids.effects;

import ch.coredump.asteroids.GameManager;
import processing.core.PApplet;

public class StarField {
	public static final int SF_NUM_STARS = 100;
	public static final int SF_NUM_LAYERS = 3;
	GameManager gameManager;
	int width;
	int height;
	int numStars;
	int layers;

	Star[][] starField;

	public StarField(GameManager gameManager, int width, int height) {
		this.gameManager = gameManager;
		this.width = width;
		this.height = height;

		init();
	}

	public void init() {
		init(SF_NUM_LAYERS, SF_NUM_STARS);
	}

	/**
	 * Creates the star field by placing x layers of random stars.
	 */
	public void init(int layers, int numStars) {
		this.layers = layers;
		this.numStars = numStars;

		starField = new Star[layers][numStars];

		// fill star field with x levels of random stars
		for (int level = 0; level < layers; level++) {
			for (int star = 0; star < numStars; star++) {
				float rWidth = (float) Math.random() * width;
				float rHeight = (float) Math.random() * height;
				starField[level][star] = new Star(rWidth, rHeight);
			}
		}
	}

	public void update(float tpf) {
		final float playerX = gameManager.getSpaceship().getX();
		final float playerY = gameManager.getSpaceship().getY();
		// player offset from center of screen
		float playerOffsetX = playerX - width / 2;
		float playerOffsetY = playerY - height / 2;

		float speedFactorPerLayer = 0.2f;

		// update x/y stars offset
		for (int layer = 0; layer < layers; layer++) {
			final float layerSpeedFactor = (layers - layer) * speedFactorPerLayer;

			for (int star = 0; star < numStars; star++) {
				Star s = starField[layer][star];
				s.xOffset = playerOffsetX * layerSpeedFactor;
				s.yOffset = playerOffsetY * layerSpeedFactor;
			}
		}

		drift(tpf, speedFactorPerLayer);
	}

	/**
	 * move stars very slowly (drifting)
	 */
	private void drift(float tpf, float speedFactorPerLayer) {
		float speed = -0.1f;

		for (int layer = 0; layer < layers; layer++) {
			for (int star = 0; star < numStars; star++) {
				final Star s = starField[layer][star];

				// layer 0 fastest, layer >0 slower
				float velPerFrame = speed * tpf;
				velPerFrame *= (layers - layer) * speedFactorPerLayer;

				float newX = s.getX() + velPerFrame;
				newX %= width;
				s.setX(newX);

//				float newY = s.getY() + velPerFrame;
//				newY %= height;
//				s.setY(newY);
			}
		}
	}

	public void render(PApplet p) {
		p.push();

		for (int level = 0; level < layers; level++) {
			switch (level) {
			case 0:
				p.stroke(255, 255, 0, 200);
				p.strokeWeight(1);
				break;
			case 1:
				p.stroke(255, 0, 0, 150);
				p.strokeWeight(1);
				break;
			case 2:
				p.stroke(0, 155, 255, 100);
				p.strokeWeight(1);
				break;
			default:
				break;
			}

			for (Star s : starField[level]) {
				float newX = (s.getX() + -s.getXOffset()) % width;
				if (newX < 0) {
					newX = width + newX;
				}

				float newY = (s.getY() + -s.getYOffset()) % height;
				if (newY < 0) {
					newY = height + newY;
				}
				p.point(newX, newY);
			}
		}
		p.pop();
	}

}
