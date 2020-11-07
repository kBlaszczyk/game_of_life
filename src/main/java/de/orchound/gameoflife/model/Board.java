package de.orchound.gameoflife.model;

import org.joml.Vector2i;
import org.joml.Vector2ic;

public class Board {

	public final Vector2ic size;

	private final boolean[] initial;
	public boolean[] target;
	private boolean[] source;

	public Board(int width, int height) {
		size = new Vector2i(width, height);
		int cellsCount = width * height;

		source = new boolean[cellsCount];
		target = new boolean[cellsCount];
		initial = new boolean[cellsCount];
	}

	public void update() {
		boolean[] temp = source;
		source = target;
		target = temp;

		for (int i = 0; i < size.y(); i++) {
			for (int j = 0; j < size.x(); j++) {
				int livingNeighborsCount = countLivingNeighbors(i, j);
				boolean sourceCell = source[i * size.x() + j];
				target[i * size.x() + j] = determineCellStatus(sourceCell, livingNeighborsCount);
			}
		}
	}

	private int countLivingNeighbors(int rowIndex, int cellIndex) {
		int livingNeighborsCount = 0;

		for (int xOffset = -1; xOffset <= 1; xOffset++) {
			for (int yOffset = - 1; yOffset <= 1; yOffset++) {
				if (!(xOffset == 0 && yOffset == 0)) {
					int neighborX = Math.floorMod(cellIndex + xOffset, size.x());
					int neighborY = Math.floorMod(rowIndex + yOffset, size.y());
					livingNeighborsCount += source[neighborY * size.x() + neighborX] ? 1 : 0;
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
		return target[rowIndex * size.x() + cellIndex];
	}

	public void resurrectCell(int rowIndex, int cellIndex) {
		if (cellIndex >= 0 & cellIndex < size.x())
			target[rowIndex * size.x() + cellIndex] = true;
	}

	public void killCell(int rowIndex, int cellIndex) {
		if (cellIndex >= 0 & cellIndex < size.x())
			target[rowIndex * size.x() + cellIndex] = false;
	}

	public int getWidth() {
		return size.x();
	}

	public int getHeight() {
		return size.y();
	}

	public void reset() {
		System.arraycopy(initial, 0, target, 0, initial.length);
	}

	public void makeCurrentStateInitial() {
		System.arraycopy(target, 0, initial, 0, target.length);
	}
}
