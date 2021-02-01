package de.orchound.gameoflife.painting;

import org.joml.Vector2ic;

public interface Painter {
	void paint(Vector2ic cell);
	void stopPainting();
}
