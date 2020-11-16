package de.orchound.gameoflife.processing;

import processing.core.PApplet;

public class PauseButton extends Button {

	private final PApplet sketch;
	private boolean paused = true;

	public PauseButton(float x, float y, PApplet sketch, Runnable action) {
		super(40, 40, action);
		setPosition(x, y);
		this.sketch = sketch;
	}

	@Override
	public void draw() {
		sketch.pushStyle();

		if (paused) {
			sketch.triangle(
				position.x(), position.y(),
				position.x(), position.y() + 40,
				position.x() + 40, position.y() + 20
			);
		} else {
			sketch.rect(position.x(), position.y(), 13, size.y());
			sketch.rect(position.x() + 27, position.y(), 13, size.y());
		}

		sketch.popStyle();
	}

	/**
	 * Makes the button represent a play- or pause button,
	 * depending on if the specified value is true or false respectively.
	 * @param paused if true the button represents play, pause otherwise
	 */
	public void setPaused(boolean paused) {
		this.paused = paused;
	}
}
