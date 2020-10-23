package de.orchound.gameoflife.game;

import java.util.Random;

public class Board {

	Random random = new Random();

	public boolean[][] target;
	private boolean[][] source;

	public Board(int width, int height) {
		source = new boolean[height][width];
		target = new boolean[height][width];

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
