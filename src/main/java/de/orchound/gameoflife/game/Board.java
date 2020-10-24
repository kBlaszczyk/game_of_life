package de.orchound.gameoflife.game;

import java.util.Random;

public class Board {

	public final int width;
	public final int height;

	public boolean[][] target;
	private boolean[][] source;

	public Board(int width, int height) {
		this.width = width;
		this.height = height;
		source = new boolean[height][width];
		target = new boolean[height][width];

		Random random = new Random();

		for (int i = 0; i < target.length; i++) {
			for (int j = 0; j < target[0].length; j++) {
				target[i][j] = random.nextBoolean();
			}
		}
	}

	public void update() {
		boolean[][] temp = source;
		source = target;
		target = temp;

		for (int i = 0; i < target.length; i++) {
			for (int j = 0; j < target[0].length; j++) {
				target[i][j] = !source[i][j];
			}
		}
	}
}
