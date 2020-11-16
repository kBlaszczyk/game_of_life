package de.orchound.gameoflife.processing;

import de.orchound.gameoflife.processing.ui.UIElement;
import org.joml.Vector2f;

public abstract class Button implements UIElement {

	public final Vector2f position = new Vector2f();
	public final Vector2f size;
	public final Vector2f halfSize;

	private final Runnable action;

	private final Vector2f mousePositionBuffer = new Vector2f();

	public Button(float width, float height, Runnable action) {
		this.size = new Vector2f(width, height);
		this.halfSize = new Vector2f(size).div(2f);
		this.action = action;
	}

	public void click(float x, float y) {
		Vector2f positionButtonSpace = mousePositionBuffer.set(x, y)
			.sub(position).sub(halfSize).absolute();

		if (positionButtonSpace.x <= halfSize.x() && positionButtonSpace.y <= halfSize.y())
			action.run();
	}

	@Override
	public void setPosition(float x, float y) {
		position.set(x, y);
	}

	@Override
	public float getWidth() {
		return size.x;
	}

	@Override
	public void setWidth(float value) {
		size.x = value;
	}
}
