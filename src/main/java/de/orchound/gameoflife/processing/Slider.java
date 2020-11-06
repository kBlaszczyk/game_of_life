package de.orchound.gameoflife.processing;

import org.joml.Vector2f;
import org.joml.Vector2fc;
import processing.core.PApplet;

import java.util.function.Consumer;

public class Slider {

	private final Vector2fc position;
	private final Vector2fc size = new Vector2f(60, 30);
	private final Vector2fc halfSize = new Vector2f(size).div(2f);
	private final float handleWidth = 8f;
	private float value = 0.5f;

	private final PApplet sketch;
	private final Consumer<Float> action;

	public Slider(float x, float y, PApplet sketch, Consumer<Float> action) {
		position = new Vector2f(x, y);

		this.sketch = sketch;
		this.action = action;
	}

	public void draw() {
		sketch.pushStyle();

		sketch.rect(position.x(),position.y(), size.x(), size.y());
		final float handleX = position.x() + value * (position.x() + size.x() - position.x());

		sketch.fill(0);
		final float handleHalfWidth = handleWidth / 2f;
		sketch.rect(handleX - handleHalfWidth, position.y(), handleWidth, size.y());

		sketch.popStyle();
	}

	public void drag(float x, float y) {
		Vector2f positionSliderSpace = new Vector2f(x, y)
			.sub(position).sub(halfSize).absolute();

		if (positionSliderSpace.x <= halfSize.x() && positionSliderSpace.y <= halfSize.y()) {
			float newValue = PApplet.map(x, position.x(), position.x() + size.x(), 0f, 1f);
			action.accept(Math.min(1f, Math.max(0f, newValue)));
		}
	}

	public void setValue(float newValue) {
		value = newValue;
	}
}
