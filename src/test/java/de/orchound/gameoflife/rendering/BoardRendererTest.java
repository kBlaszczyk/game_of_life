package de.orchound.gameoflife.rendering;

import de.orchound.gameoflife.game.Board;
import org.joml.Vector2f;
import org.joml.Vector2i;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import processing.core.PApplet;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class BoardRendererTest {

	@Mock
	private PApplet sketch;
	private BoardRenderer renderer;

	@BeforeEach
	public void setUp() {
		renderer = new BoardRenderer(new Board(10, 5), sketch);
	}

	@Test
	public void testGetCellAt() {
		Vector2i result = renderer.getCellAt(new Vector2f(0, 0), new Vector2i());
		assertEquals(new Vector2i(5, 2), result);

		renderer.getCellAt(new Vector2f(-4.8f, -2.4f), result);
		assertEquals(new Vector2i(0, 0), result);

		renderer.getCellAt(new Vector2f(4.8f, -2.4f), result);
		assertEquals(new Vector2i(9, 0), result);

		renderer.getCellAt(new Vector2f(-4.8f, 2.4f), result);
		assertEquals(new Vector2i(0, 4), result);

		renderer.getCellAt(new Vector2f(4.8f, 2.4f), result);
		assertEquals(new Vector2i(9, 4), result);
	}
}
