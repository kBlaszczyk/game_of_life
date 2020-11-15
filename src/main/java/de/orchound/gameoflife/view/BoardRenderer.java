package de.orchound.gameoflife.view;

import processing.core.PImage;

public interface BoardRenderer {
	void render(boolean[] data, PImage target);
}
