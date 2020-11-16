package de.orchound.gameoflife.processing.ui;

import org.joml.Vector2f;
import processing.core.PApplet;

import java.util.ArrayList;
import java.util.List;

public class UIElementContainer implements UIElement {

	private final PApplet sketch;
	private final List<UIElement> elements = new ArrayList<>();
	public final Vector2f position;
	public final Vector2f size = new Vector2f();

	public UIElementContainer(float x, float y, PApplet sketch) {
		position = new Vector2f(x, y);
		this.sketch = sketch;
	}

	public void addElement(UIElement element) {
		element.setPosition(0, elements.size() * 40);
		elements.add(element);
		size.set(Math.max(size.x, element.getWidth()), size.y + 40);
		for (UIElement uiElement : elements) {
			uiElement.setWidth(size.x);
		}
	}

	@Override
	public void draw() {
		sketch.pushMatrix();
		sketch.translate(position.x(), position.y());
		elements.forEach(UIElement::draw);
		sketch.popMatrix();
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
