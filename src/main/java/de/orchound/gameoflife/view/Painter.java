package de.orchound.gameoflife.view;

import de.orchound.gameoflife.model.Game;
import org.joml.Vector2ic;

public class Painter {

	private final Game game;
	private boolean currentlyPainting = false;
	private boolean paintMode = false;

	public Painter(Game game) {
		this.game = game;
	}

	public void paintOrEraseCell(Vector2ic cell) {
		if (!currentlyPainting) {
			paintMode = !game.getCellStatus(cell);
			currentlyPainting = true;
		}

		game.setCell(cell, paintMode);
	}

	public void stopPainting() {
		currentlyPainting = false;
	}
}
