package de.orchound.gameoflife.painting;

import de.orchound.gameoflife.model.Game;
import org.joml.Vector2i;
import org.joml.Vector2ic;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TogglePainterTest {

	Game game = new Game(6, 6);
	TogglePainter painter = new TogglePainter(game);
	@BeforeEach
	void setUp() {
		game.clear();
		game.setCell(new Vector2i(2, 1), true);
		game.setCell(new Vector2i(2, 3), true);
	}

	@Test
	void testPaint() {

		for (int i = 0; i < 3; i++) {
			Vector2ic cell = new Vector2i(2, i);
			painter.paint(cell);
			assertTrue(game.getCellStatus(cell));
		}

		painter.stopPainting();
		Vector2ic cell = new Vector2i(2, 3);
		painter.paint(new Vector2i(2, 3));
		assertFalse(game.getCellStatus(cell));
	}
}
