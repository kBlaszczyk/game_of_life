package de.orchound.gameoflife.rendering;

import de.orchound.gameoflife.game.Board;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PImage;

public class BoardRenderer {

	private final Board board;
	private final PApplet sketch;

	private final float halfBoardWidth;
	private final float halfBoardHeight;

	private final PImage boardRepresentation;
	private final int setColor;
	private final int unsetColor;

	public BoardRenderer(Board board, PApplet sketch) {
		this.board = board;
		halfBoardWidth = board.width / 2f;
		halfBoardHeight = board.height / 2f;

		this.sketch = sketch;
		setColor = sketch.color(115, 210, 22);
		unsetColor = sketch.color(0);

		boardRepresentation = sketch.createImage(board.width, board.height, PConstants.RGB);
		update();
	}

	public void render() {
		sketch.image(boardRepresentation, -halfBoardWidth, -halfBoardHeight);
	}

	public void update() {
		boolean[][] data = board.target;

		boardRepresentation.loadPixels();

		for (int i = 0; i < board.height; i++) {
			for (int j = 0; j < board.width; j++) {
				boardRepresentation.pixels[i * board.width + j] = data[i][j] ? setColor : unsetColor;
			}
		}

		boardRepresentation.updatePixels();
	}
}
