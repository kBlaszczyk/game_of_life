package de.orchound.gameoflife.processing.ui;

public interface UIElement {

	void draw();
	void setPosition(float x, float y);

	float getWidth();
	void setWidth(float value);
}
