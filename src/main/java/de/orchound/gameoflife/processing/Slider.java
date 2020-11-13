package de.orchound.gameoflife.processing;

import de.orchound.gameoflife.MouseInputEvent;
import org.joml.Vector2f;
import org.joml.Vector2fc;
import processing.core.PApplet;

import java.util.function.Consumer;

public class Slider {

	private float value = 0.5f;

	private final Vector2fc position;
	private final Vector2fc size = new Vector2f(60, 30);
	private final Vector2fc halfSize = new Vector2f(size).div(2f);
	private final float handleWidth = 8f;
	private final float handleHalfWidth = handleWidth / 2f;

	private final PApplet sketch;
	private final Consumer<Float> action;

	private final Vector2f mousePositionBuffer = new Vector2f();

	public Slider(float x, float y, PApplet sketch, Consumer<Float> action) {
		position = new Vector2f(x, y);

		this.sketch = sketch;
		this.action = action;
		action.accept(value);
	}

	public void draw() {
		sketch.pushStyle();

		sketch.rect(position.x(),position.y(), size.x(), size.y());

		sketch.fill(0);
		final float handleX = position.x() + value * (position.x() + size.x() - position.x());
		sketch.rect(handleX - handleHalfWidth, position.y(), handleWidth, size.y());

		sketch.popStyle();
	}

	public void handleMouseInput(MouseInputEvent inputEvent) {
		if (!mouseInputRelevant(inputEvent))
			return;

		value = (inputEvent.getMouseX() - position.x()) / size.x();
		action.accept(Math.min(1f, Math.max(0f, value)));
		inputEvent.consume();
	}

	private boolean mouseInputRelevant(MouseInputEvent inputEvent) {
		return (inputEvent.getLeftKey()
			&& (inputEvent.isPressed() || inputEvent.isDragged())
			&& hitByCursor(inputEvent.getMouseX(), inputEvent.getMouseY())
		);
	}

	private boolean hitByCursor(int x, int y) {
		Vector2f positionSliderSpace = mousePositionBuffer.set(x, y)
			.sub(position).sub(halfSize).absolute();

		return positionSliderSpace.x <= halfSize.x() && positionSliderSpace.y <= halfSize.y();
	}
}
