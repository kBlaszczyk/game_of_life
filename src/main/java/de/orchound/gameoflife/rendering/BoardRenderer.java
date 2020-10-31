package de.orchound.gameoflife.rendering;

import de.orchound.gameoflife.game.Board;
import org.joml.RoundingMode;
import org.joml.Vector2f;
import org.joml.Vector2fc;
import org.joml.Vector2i;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PImage;

public class BoardRenderer {

	private final Board board;
	private final PApplet sketch;

	private final Vector2fc halfBoardSize;

	private final PImage boardImage;
	private final int setColor;
	private final int unsetColor;

	private final Vector2f bufferVector2f = new Vector2f();

	public BoardRenderer(Board board, PApplet sketch) {
		this.board = board;
		halfBoardSize = new Vector2f(board.size).div(2f);

		this.sketch = sketch;
		setColor = sketch.color(115, 210, 22);
		unsetColor = sketch.color(0);

		boardImage = sketch.createImage(board.getWidth(), board.getHeight(), PConstants.RGB);
	}

	public void render() {
		sketch.image(boardImage, -halfBoardSize.x(), -halfBoardSize.y());
	}

	public void update() {
		boolean[] data = board.target;

		boardImage.loadPixels();

		for (int i = 0; i < data.length; i++) {
			boardImage.pixels[i] = data[i] ? setColor : unsetColor;
		}

		boardImage.updatePixels();
	}

	public Vector2i getCellAt(Vector2fc position, Vector2i target) {
		bufferVector2f.set(halfBoardSize)
			.sub((int) halfBoardSize.x(), (int) halfBoardSize.y())
			.add(position);

		return target.set(bufferVector2f, RoundingMode.FLOOR)
			.add((int) halfBoardSize.x(), (int) halfBoardSize.y());
	}
}
