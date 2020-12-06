package de.orchound.gameoflife.view;

import de.orchound.gameoflife.MouseInputEvent;
import de.orchound.gameoflife.model.Game;
import org.joml.Vector2i;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PImage;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BoardViewTest {

	@Mock
	private PApplet sketch;

	@Mock
	private BoardRenderer boardRenderer;

	@Spy
	private final Game game = new Game(10, 5, "B3/S23");

	private BoardView boardView;

	@BeforeEach
	public void setUp() {
		when(sketch.createImage(10, 5, PConstants.RGB))
			.thenReturn(new PImage(10, 5));
		boardView = new BoardView(game, sketch, boardRenderer);
	}

	@Test
	public void testGetCellAt() {
		MouseInputEvent inputEvent = new MouseInputEvent();
		inputEvent.setPressed();
		inputEvent.setLeftKey();
		inputEvent.setMousePosition(0, 0);

		boardView.handleMouseInput(inputEvent);
		verify(game).toggleCell(ArgumentMatchers.eq(new Vector2i(5, 2)));
	}
}
