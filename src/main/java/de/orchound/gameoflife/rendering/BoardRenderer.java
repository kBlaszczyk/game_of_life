package de.orchound.gameoflife.rendering;

import de.orchound.gameoflife.game.Board;
import processing.core.PApplet;

public class BoardRenderer {

	private final Board board;
	private final PApplet sketch;

	private final float halfBoardWidth;
	private final float halfBoardHeight;

	public BoardRenderer(Board board, PApplet sketch) {
		this.board = board;
		this.sketch = sketch;

		halfBoardWidth = board.width / 2f;
		halfBoardHeight = board.height / 2f;
	}

	public void render() {
		sketch.pushStyle();
		sketch.strokeWeight(0f);

		boolean[][] data = board.target;
		for (int i = 0; i < data.length; i++) {
			for (int j = 0; j < data[0].length; j++) {
				if (data[i][j]) {
					drawCell(i, j);
				}
			}
		}

		sketch.popStyle();
	}

	private void drawCell(int x, int y) {
		sketch.pushMatrix();
		sketch.pushStyle();

		sketch.translate(y - halfBoardWidth, x - halfBoardHeight);
		sketch.fill(115f, 210f, 22f);
		sketch.rect(0.05f, 0.05f, 0.9f, 0.9f);

		sketch.popStyle();
		sketch.popMatrix();
	}
}
