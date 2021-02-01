package de.orchound.gameoflife.painting;

import de.orchound.gameoflife.model.Game;
import org.joml.Vector2ic;

public class TogglePainter implements Painter {

	private final Game game;
	private boolean currentlyPainting = false;
	private boolean paintMode = false;

	public TogglePainter(Game game) {
		this.game = game;
	}

	@Override
	public void paint(Vector2ic cell) {
		if (!currentlyPainting) {
			paintMode = !game.getCellStatus(cell);
			currentlyPainting = true;
		}
		game.setCell(cell, paintMode);
	}

	@Override
	public void stopPainting() {
		currentlyPainting = false;
	}
}
