package de.orchound.gameoflife.painting;

import de.orchound.gameoflife.model.Game;
import org.joml.Vector2i;
import org.joml.Vector2ic;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FillPainterTest {
	Game game = new Game(6, 8);
	FillPainter fillPainter = new FillPainter(game);

	@BeforeEach
	void setUp() {
		game.clear();
	}

	@Test
	void testFillBoard() {
		Vector2ic cell = new Vector2i(5, 5);
		fillPainter.paint(cell);
		for (int i = 0; i < game.getSize().x(); i++) {
			for (int j = 0; j < game.getSize().y(); j++) {
				assertTrue(game.getCellStatus(new Vector2i(i, j)));
			}
		}
	}

	@Test
	void testFillSquare() {
		for (int i = 1; i < 5; i++) {
			game.setCell(new Vector2i(i, 1), true);
			game.setCell(new Vector2i(1, i), true);
			game.setCell(new Vector2i(4, i), true);
			game.setCell(new Vector2i(i, 4), true);
		}

		Vector2ic cell = new Vector2i(2, 2);
		fillPainter.paint(cell);

		for (int i = 0; i < 6; i++) {
			assertFalse(game.getCellStatus(new Vector2i(0, i)));
			assertFalse(game.getCellStatus(new Vector2i(i, 0)));
			assertFalse(game.getCellStatus(new Vector2i(5, i)));
			assertFalse(game.getCellStatus(new Vector2i(i, 5)));
		}
	}
}
