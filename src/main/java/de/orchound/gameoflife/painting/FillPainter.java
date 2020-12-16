package de.orchound.gameoflife.painting;

import de.orchound.gameoflife.model.Game;
import org.joml.Vector2i;
import org.joml.Vector2ic;

public class FillPainter implements Painter {

	private final Game game;
	private boolean currentlyPainting = false;
	private boolean paintMode = false;

	public FillPainter(Game game) {
		this.game = game;
	}

	private void fillCells(Vector2ic cell) {
		if (game.getCellStatus(cell) != paintMode && game.cellInBoardRange(cell)) {
			game.setCellSilently(cell, paintMode);
			fillCells(new Vector2i(cell.x(), cell.y()+1));
			fillCells(new Vector2i(cell.x(), cell.y()-1));
			fillCells(new Vector2i(cell.x() + 1, cell.y()));
			fillCells(new Vector2i(cell.x() - 1, cell.y()));
		}
	}

	@Override
	public void paint(Vector2ic cell)  {
		if (!currentlyPainting) {
			paintMode = !game.getCellStatus(cell);
			fillCells(cell);
			game.stateChangeComplete();
			currentlyPainting = true;
		}
	}

	@Override
	public void stopPainting() {
		currentlyPainting = false;
	}
}
