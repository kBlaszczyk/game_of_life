package de.orchound.gameoflife.painting;

import de.orchound.gameoflife.model.Game;
import org.joml.Vector2i;
import org.joml.Vector2ic;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TogglePainterTest {

	Game game = new Game(3, 1);
	TogglePainter painter = new TogglePainter(game);
	Vector2ic cell1 = new Vector2i(0, 0);
	Vector2ic cell2 = new Vector2i(1, 0);
	Vector2ic cell3 = new Vector2i(2, 0);

	@BeforeEach
	void setUp() {
		game.clear();
	}

	@Test
	void testPaintFalseToTrue() {

		game.setCell(cell2, true);

		painter.paint(cell1);
		painter.paint(cell2);
		painter.paint(cell2);

		assertTrue(game.getCellStatus(cell1));
		assertTrue(game.getCellStatus(cell2));
		assertTrue(game.getCellStatus(cell3));
	}

	@Test
	void testPaintTrueToFalse() {

		game.setCell(cell1, true);
		game.setCell(cell3, true);

		painter.paint(cell1);
		painter.paint(cell2);
		painter.paint(cell3);

		assertFalse(game.getCellStatus(cell1));
		assertFalse(game.getCellStatus(cell2));
		assertFalse(game.getCellStatus(cell3));
	}

	@Test
	void testStopPainting() {
		game.setCell(cell2, true);
		painter.paint(cell1);
		painter.stopPainting();
		painter.paint(cell2);

		assertTrue(game.getCellStatus(cell1));
		assertFalse(game.getCellStatus(cell2));

	}
}
