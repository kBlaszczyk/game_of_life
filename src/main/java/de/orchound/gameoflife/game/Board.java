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
				int livingNeighborsCount = countLivingNeighbors(i, j);
				target[i][j] = determineCellStatus(source[i][j], livingNeighborsCount);
			}
		}
	}

	private int countLivingNeighbors(int rowIndex, int cellIndex) {

		final int height = source.length;
		final int width = source[0].length;
		int livingNeighborsCount = 0;

		for (int xOffset = -1; xOffset <= 1; xOffset++) {
			for (int yOffset = - 1; yOffset <= 1; yOffset++) {
				if (!(xOffset == 0 && yOffset == 0)) {
					int neighborX = cellIndex + xOffset;
					int neighborY = rowIndex + yOffset;
					if (neighborX >= 0 && neighborX < width && neighborY >= 0 && neighborY < height) {
						livingNeighborsCount += source[neighborY][neighborX] ? 1 : 0;
					}
				}
			}
		}
		return livingNeighborsCount;
	}

	private boolean determineCellStatus(boolean cell, int livingNeighborsCount) {
		return cell ? livingNeighborsCount >= 2 && livingNeighborsCount <= 3 :
			livingNeighborsCount == 3;
	}

	public void setCellAlive(int rowIndex, int cellIndex) {
		target[rowIndex][cellIndex] = true;
	}
}
