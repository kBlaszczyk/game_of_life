package de.orchound.gameoflife.game;

import java.util.Random;

public class Board {

	public final int width;
	public final int height;

	private boolean[] initial;
	public boolean[] target;
	private boolean[] source;

	public Board(int width, int height) {
		this.width = width;
		this.height = height;
		source = new boolean[width * height];
		target = new boolean[width * height];
		randomize();
	}

	public void randomize() {
		Random random = new Random();

		for (int i = 0; i < target.length; i++) {
			target[i] = random.nextBoolean();

			// initial equals whatever target was, when it was randomized
			initial = target.clone();
		}
	}

	public void update() {
		boolean[] temp = source;
		source = target;
		target = temp;

		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				int livingNeighborsCount = countLivingNeighbors(i, j);
				boolean sourceCell = source[i * width + j];
				target[i * width + j] = determineCellStatus(sourceCell, livingNeighborsCount);
			}
		}
	}

	private int countLivingNeighbors(int rowIndex, int cellIndex) {
		int livingNeighborsCount = 0;

		for (int xOffset = -1; xOffset <= 1; xOffset++) {
			for (int yOffset = - 1; yOffset <= 1; yOffset++) {
				if (!(xOffset == 0 && yOffset == 0)) {
					int neighborX = Math.floorMod(cellIndex + xOffset, width);
					int neighborY = Math.floorMod(rowIndex + yOffset, height);
					livingNeighborsCount += source[neighborY * width + neighborX] ? 1 : 0;
				}
			}
		}
		return livingNeighborsCount;
	}

	private boolean determineCellStatus(boolean cell, int livingNeighborsCount) {
		return cell ? livingNeighborsCount >= 2 && livingNeighborsCount <= 3 :
			livingNeighborsCount == 3;
	}

	public boolean getCellStatus(int rowIndex, int cellIndex) {
		return target[rowIndex * width + cellIndex];
	}

	public void resurrectCell(int rowIndex, int cellIndex) {
		target[rowIndex * width + cellIndex] = true;
	}

	public void killCell(int rowIndex, int cellIndex) {
		target[rowIndex * width + cellIndex] = false;
	}

	public void reset() {
		target = initial.clone();
	}
}
