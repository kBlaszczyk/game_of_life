package de.orchound.gameoflife.view;

import de.orchound.gameoflife.model.Game;
import org.joml.*;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PImage;

public class BoardView {

	private final PApplet sketch;

	public final Vector2fc size;
	private final Vector2fc halfSize;

	private final PImage boardImage;
	private final int setColor;
	private final int unsetColor;

	private final Vector2f bufferVector2f = new Vector2f();

	public BoardView(Game game, PApplet sketch) {
		final Vector2ic boardSize = game.getSize();
		size = new Vector2f(boardSize);
		halfSize = new Vector2f(size).div(2f);

		this.sketch = sketch;
		setColor = sketch.color(115, 210, 22);
		unsetColor = sketch.color(0);

		boardImage = sketch.createImage(boardSize.x(), boardSize.y(), PConstants.RGB);
		game.registerBoardDataObserver(this::updateImage);
	}

	public void draw() {
		sketch.image(boardImage, -halfSize.x(), -halfSize.y());
	}

	private void updateImage(boolean[] data) {
		boardImage.loadPixels();

		for (int i = 0; i < data.length; i++) {
			boardImage.pixels[i] = data[i] ? setColor : unsetColor;
		}

		boardImage.updatePixels();
	}

	public Vector2i getCellAt(Vector2fc position, Vector2i target) {
		bufferVector2f.set(halfSize)
			.sub((int) halfSize.x(), (int) halfSize.y())
			.add(position);

		return target.set(bufferVector2f, RoundingMode.FLOOR)
			.add((int) halfSize.x(), (int) halfSize.y());
	}
}
