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
	}

	@Test
	void testPaint() {
		game.clear();
		Vector2ic cell = new Vector2i(2, 2);
		painter.paint(cell);

		assertTrue(game.getCellStatus(new Vector2i(2, 2)));


		for (int i = 0; i <= game.getSize().x(); i++) {
			for (int j = 0; j <= game.getSize().y(); j++) {
				if (i != 2 && j != 2) {
					assertFalse(game.getCellStatus(new Vector2i(i, j)));
				}
			}
		}
	}
}
