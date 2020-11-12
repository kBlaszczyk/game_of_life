package de.orchound.gameoflife.view;

import processing.core.PImage;

import java.awt.Color;

public class TrailBoardRenderer implements BoardRenderer {

	private final int setColor = Color.WHITE.getRGB();
	private final int unsetColor = Color.BLACK.getRGB();
	private final int trailColor = Color.BLUE.getRGB();

	@Override
	public void render(boolean[] data, PImage target) {
		target.loadPixels();

		for (int i = 0; i < data.length; i++) {
			int currentColor = target.pixels[i];
			if (data[i]) {
				target.pixels[i] = setColor;
			} else if (currentColor != unsetColor) {
				target.pixels[i] = trailColor(currentColor);
			}
		}

		target.updatePixels();
	}

	private int trailColor(int currentColor) {
		final int newAlpha = Math.max(0, getAlpha(currentColor) - 10);
		return newAlpha > 0 ? setAlpha(trailColor, newAlpha) : unsetColor;
	}

	private int setAlpha(int color, int alpha) {
		int cleanAlpha = alpha << 24;
		return color & 0x00FFFFFF | cleanAlpha;
	}

	private int getAlpha(int color) {
		return (color >> 24) & 0xFF;
	}
}
