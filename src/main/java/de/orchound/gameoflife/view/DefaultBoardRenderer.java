package de.orchound.gameoflife.view;

import processing.core.PImage;

import java.awt.*;

public class DefaultBoardRenderer implements BoardRenderer {

	private final int setColor = new Color(115, 210, 22).getRGB();
	private final int unsetColor = new Color(0).getRGB();

	@Override
	public void render(boolean[] data, PImage target) {
		target.loadPixels();

		for (int i = 0; i < data.length; i++) {
			target.pixels[i] = data[i] ? setColor : unsetColor;
		}

		target.updatePixels();
	}
}
