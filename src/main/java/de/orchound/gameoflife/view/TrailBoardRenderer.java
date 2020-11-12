package de.orchound.gameoflife.view;

import processing.core.PImage;

import java.awt.*;

public class TrailBoardRenderer implements BoardRenderer {

	private final Color setColor = Color.WHITE;
	private final Color unsetColor = Color.BLACK;
	private final Color trailColor = Color.BLUE;

	@Override
	public void render(boolean[] data, PImage target) {
		target.loadPixels();

		for (int i = 0; i < data.length; i++) {
			if (data[i]) {
				target.pixels[i] = setColor.getRGB();
			} else {
				Color pixel = new Color(target.pixels[i], true);
				if (pixel.getRGB() != unsetColor.getRGB()) {
					if (pixel.getAlpha() > 0) {
						int newAlpha = Math.max(0, pixel.getAlpha() - 10);
						if (newAlpha > 0) {
							target.pixels[i] = new Color(
								trailColor.getRed(), trailColor.getGreen(), trailColor.getBlue(),
								newAlpha
							).getRGB();
						} else {
							target.pixels[i] = unsetColor.getRGB();
						}
					}
				}
			}
		}

		target.updatePixels();
	}
}
