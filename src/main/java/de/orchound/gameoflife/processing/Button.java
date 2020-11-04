package de.orchound.gameoflife.processing;

import org.joml.Vector2f;
import org.joml.Vector2fc;

public abstract class Button {

	public final Vector2fc position;
	public final Vector2fc size;
	public final Vector2fc halfSize;

	private final Runnable action;

	public Button(float x, float y, float width, float height, Runnable action) {
		this.position = new Vector2f(x, y);
		this.size = new Vector2f(width, height);
		this.halfSize = new Vector2f(size).div(2f);
		this.action = action;
	}

	public void click(float x, float y) {
		Vector2f positionButtonSpace = new Vector2f(x, y)
			.sub(position).sub(halfSize).absolute();

		if (positionButtonSpace.x <= halfSize.x() && positionButtonSpace.y <= halfSize.y())
			action.run();
	}

	public abstract void draw();
}
