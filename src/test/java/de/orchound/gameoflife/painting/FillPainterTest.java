package de.orchound.gameoflife.painting;

import de.orchound.gameoflife.model.Game;
import org.joml.Vector2i;
import org.joml.Vector2ic;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FillPainterTest {
	Game game = new Game(6, 6);
	FillPainter fillPainter = new FillPainter(game);

	@BeforeEach
	void setUp() {
		game.clear();
	}

	@Test
	void testPaint() {
		game.clear();

		for (int i = 0; i < 3; i ++) {
			game.toggleCell(new Vector2i(i, 2));
		}

		for (int i = 0; i < 2; i ++) {
			game.toggleCell(new Vector2i(2, i));
		}

		Vector2ic cell = new Vector2i(1, 1);

		fillPainter.paint(cell);

		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				assertTrue(game.getCellStatus(new Vector2i(i, j)));
			}
		}

		for (int i = 3; i < 6; i++ ) {
			for (int j = 0; j < 6; j++) {
				assertFalse(game.getCellStatus(new Vector2i(i, j)));
			}
		}

		for (int i = 0; i < 3; i++ ) {
			for (int j = 3; j < 6; j++) {
				assertFalse(game.getCellStatus(new Vector2i(i, j)));
			}
		}
	}
}
