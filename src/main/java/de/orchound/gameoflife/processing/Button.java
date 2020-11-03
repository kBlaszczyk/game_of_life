package de.orchound.gameoflife.processing;

import org.joml.Vector2f;
import org.joml.Vector2fc;
import processing.core.PApplet;

public class Button {

	public final Vector2fc position;
	public final Vector2fc size;
	public final Vector2fc halfSize;

	private final String label;
	private final PApplet sketch;
	private final Runnable action;

	public Button(float x, float y, String label, PApplet sketch, Runnable action) {
		this.position = new Vector2f(x, y);
		this.label = label;
		this.sketch = sketch;
		this.action = action;

		final float width = sketch.textWidth(label) + 20;
		final float height = sketch.textAscent() + sketch.textDescent();
		size = new Vector2f(width, height);
		halfSize = new Vector2f(size).div(2f);
	}

	public void click(float x, float y) {
		Vector2f positionButtonSpace = new Vector2f(x, y)
			.sub(position).sub(halfSize).absolute();

		if (positionButtonSpace.x <= halfSize.x() && positionButtonSpace.y <= halfSize.y())
			action.run();
	}

	public void draw() {
		sketch.pushStyle();

		sketch.rect(position.x(), position.y(), size.x(), size.y(), 7);
		sketch.fill(0);
		sketch.text(label, position.x(), position.y(), size.x(), size.y());

		sketch.popStyle();
	}
}
