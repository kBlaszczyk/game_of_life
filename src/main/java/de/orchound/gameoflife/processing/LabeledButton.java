package de.orchound.gameoflife.processing;

import processing.core.PApplet;

public class LabeledButton extends Button {

	private final String label;
	private final PApplet sketch;

	public LabeledButton(float x, float y, String label, PApplet sketch, Runnable action) {
		super(
			x, y, sketch.textWidth(label) + 20,
			sketch.textAscent() + sketch.textDescent(), action
		);

		this.label = label;
		this.sketch = sketch;
	}

	public void draw() {
		sketch.pushStyle();

		sketch.rect(position.x(), position.y(), size.x(), size.y(), 7);
		sketch.fill(0);
		sketch.text(label, position.x(), position.y(), size.x(), size.y());

		sketch.popStyle();
	}
}
