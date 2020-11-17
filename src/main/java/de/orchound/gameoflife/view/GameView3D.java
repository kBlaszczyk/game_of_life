package de.orchound.gameoflife.view;

import de.orchound.gameoflife.MouseInputEvent;
import de.orchound.gameoflife.model.Game;
import org.joml.Vector2f;
import org.joml.Vector2fc;
import org.joml.Vector2i;
import org.joml.Vector2ic;
import processing.core.PApplet;

import java.awt.*;

public class GameView3D {
	private final PApplet sketch;
	private final Game game;
	private final float[] board;

	private float scale = 1f;

	private float yRotation = 0f;
	private float xRotation = 0f;

	public final Vector2fc size;
	private final Vector2fc halfSize;
	private final Vector2f viewOffset;

	private final int setColor;
	private final int trailColor = Color.BLUE.getRGB();

	public GameView3D(Game game, PApplet sketch) {
		this.game = game;

		Vector2i windowSize = new Vector2i(sketch.sketchWidth(), sketch.sketchHeight());
		viewOffset = new Vector2f(windowSize).div(2f);

		final Vector2ic boardSize = game.getSize();
		size = new Vector2f(boardSize);
		halfSize = new Vector2f(size).div(2f);
		board = new float[boardSize.x() * boardSize.y()];

		this.sketch = sketch;
		setColor = sketch.color(115, 210, 22);

		game.registerBoardDataObserver(this::updateBoard);
	}

	public void handleMouseInput(MouseInputEvent inputEvent) {
		if (inputEvent.isScrolled()) {
			scale = scale - inputEvent.getScrollsCount() * 0.8f;
			inputEvent.consume();
		}

		if (inputEvent.isDragged() && inputEvent.getMiddleKey()) {
			yRotation += (sketch.mouseX - sketch.pmouseX) * 0.005f;
			xRotation -= (sketch.mouseY - sketch.pmouseY) * 0.005f;
			inputEvent.consume();
		}
	}

	public void reset() {
		xRotation = 0f;
		yRotation = 0f;
		scale = 1f;
	}

	public void draw() {
		sketch.pushStyle();
		sketch.pushMatrix();

		sketch.strokeWeight(0.05f);
		sketch.fill(setColor);

		sketch.translate(viewOffset.x, viewOffset.y);
		sketch.rotateY(yRotation);
		sketch.rotateX(-PApplet.radians(90) + xRotation);
		sketch.scale(scale);

		renderBoard();

		sketch.popMatrix();
		sketch.popStyle();
	}

	private void renderBoard() {
		sketch.translate(-halfSize.x(), 0, -halfSize.y());
		int boardHeight = game.getSize().y();
		for (int row = 0; row < boardHeight; row++) {
			for (int cell = 0; cell < game.getSize().x(); cell++) {
				float cellValue = board[row * game.getSize().x() + cell];
				if (cellValue == 1f) {
					sketch.stroke(255f);
					sketch.fill(setColor);
					sketch.box(1f);
				} else if (cellValue > 0f) {
					sketch.stroke(0);
					sketch.fill(trailColor);
					float blockOffset = 0.5f - cellValue / 2f;
					sketch.translate(0f, blockOffset, 0f);
					sketch.box(1f, cellValue, 1f);
					sketch.translate(0f, -blockOffset, 0f);
				}
				sketch.translate(1f, 0f, 0f);
			}
			sketch.translate(-game.getSize().x(), 0f, 1f);
		}
	}

	private void updateBoard(boolean[] data) {
		for (int i = 0; i < data.length; i++) {
			board[i] = data[i] ? 1f : Math.max(0f, board[i] - 0.05f);
		}
	}
}
