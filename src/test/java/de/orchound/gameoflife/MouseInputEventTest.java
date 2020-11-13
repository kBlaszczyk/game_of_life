package de.orchound.gameoflife;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MouseInputEventTest {

	private final MouseInputEvent inputEvent = new MouseInputEvent();

	@BeforeEach
	public void setUp() {
		inputEvent.reset();
	}

	@Test
	public void testReset() {
		inputEvent.setLeftKey();
		inputEvent.setClicked();
		assertTrue(inputEvent.getLeftKey());
		assertTrue(inputEvent.isClicked());

		inputEvent.reset();
		assertFalse(inputEvent.getLeftKey());
		assertFalse(inputEvent.isClicked());
	}

	@Test
	public void testSettingKeys() {
		assertFalse(inputEvent.getLeftKey());
		assertFalse(inputEvent.getRightKey());
		assertFalse(inputEvent.getMiddleKey());

		inputEvent.setLeftKey();
		inputEvent.setRightKey();
		inputEvent.setMiddleKey();

		assertTrue(inputEvent.getLeftKey());
		assertTrue(inputEvent.getRightKey());
		assertTrue(inputEvent.getMiddleKey());
	}

	@Test
	public void testSettingActions() {
		assertFalse(inputEvent.isClicked());
		assertFalse(inputEvent.isPressed());
		assertFalse(inputEvent.isReleased());
		assertFalse(inputEvent.isDragged());

		inputEvent.setClicked();
		inputEvent.setPressed();
		inputEvent.setReleased();
		inputEvent.setDragged();

		assertTrue(inputEvent.isClicked());
		assertTrue(inputEvent.isPressed());
		assertTrue(inputEvent.isReleased());
		assertTrue(inputEvent.isDragged());
	}
}
