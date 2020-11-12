package de.orchound.gameoflife.view;

import de.orchound.gameoflife.MouseInputEvent;
import de.orchound.gameoflife.model.Game;
import org.joml.*;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PImage;

import java.lang.Math;

public class BoardView {

	private final PApplet sketch;
	private final Game game;
	private final BoardRenderer boardRenderer = new TrailBoardRenderer();

	private float scale;
	private final float minScale;
	private final float maxScale;

	public final Vector2fc size;
	private final Vector2fc halfSize;
	private final Vector2i windowSize;
	private final Vector2f viewOffset;

	private final PImage boardImage;
	private final int setColor;
	private final int unsetColor;

	private final Vector2f bufferVector2f = new Vector2f();
	private final Vector2i bufferVector2i = new Vector2i();

	public BoardView(Game game, PApplet sketch) {
		this.game = game;

		windowSize = new Vector2i(sketch.sketchWidth(), sketch.sketchHeight());
		viewOffset = new Vector2f(windowSize).div(2f);

		final Vector2ic boardSize = game.getSize();
		size = new Vector2f(boardSize);
		halfSize = new Vector2f(size).div(2f);

		this.sketch = sketch;
		setColor = sketch.color(115, 210, 22);
		unsetColor = sketch.color(0);

		minScale = 1f;
		maxScale = 40f;
		scale = getInitialScale();

		boardImage = sketch.createImage(boardSize.x(), boardSize.y(), PConstants.RGB);
		game.registerBoardDataObserver(this::updateImage);
	}

	private float getInitialScale() {
		return PApplet.constrain(
			Math.min((float) windowSize.x / size.x(), (float) windowSize.y / size.y()),
			minScale, maxScale
		);
	}

	public void draw() {
		windowSize.set(sketch.sketchWidth(), sketch.sketchHeight());

		sketch.pushMatrix();

		sketch.translate(viewOffset.x, viewOffset.y);
		sketch.scale(scale);
		sketch.image(boardImage, -halfSize.x(), -halfSize.y());

		sketch.popMatrix();
	}

	private void updateImage(boolean[] data) {
		boardRenderer.render(data, boardImage);
	}

	public void handleMouseInput(MouseInputEvent inputEvent) {
		if (inputEvent.isScrolled()) {
			scale = PApplet.constrain(scale - inputEvent.getScrollsCount() * 0.5f, minScale, maxScale);
			inputEvent.consume();
		}

		if (inputEvent.isPressed() && inputEvent.getLeftKey()) {
			drawCell(inputEvent.getMouseX(), inputEvent.getMouseY());
			inputEvent.consume();
		}

		if (inputEvent.isDragged() && inputEvent.getMiddleKey()) {
			viewOffset.add(sketch.mouseX - sketch.pmouseX, sketch.mouseY - sketch.pmouseY);
			inputEvent.consume();
		}
	}

	private void drawCell(int x, int y) {
		Vector2f position = bufferVector2f.set(x, y)
			.sub(viewOffset)
			.div(scale)
			.add(halfSize)
			.sub((int) halfSize.x(), (int) halfSize.y());

		Vector2i cell = bufferVector2i.set(position, RoundingMode.FLOOR)
			.add((int) halfSize.x(), (int) halfSize.y());
		game.toggleCell(cell);
	}

	public void reset() {
		viewOffset.set(windowSize).div(2f);
		scale = getInitialScale();
	}
}
