package de.orchound.gameoflife.processing;

import processing.core.PApplet;

public class LabeledButton extends Button {

	private final PApplet sketch;
	private final String label;

	public LabeledButton(String label, PApplet sketch, Runnable action) {
		super(
			sketch.textWidth(label) + 20,
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
