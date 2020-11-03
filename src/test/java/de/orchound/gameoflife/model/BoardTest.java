package de.orchound.gameoflife.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {

	Board board = new Board(20, 10);
	boolean[] initialState = board.target.clone();

	@BeforeEach
	void setUp() {
		Arrays.fill(board.target, false);
	}

	@Test
	void testSetCellAlive() {
		board.resurrectCell(1, 0);
		assertTrue(board.getCellStatus(1, 0));
	}

	@Test
	void testUpdate() {
		board.resurrectCell(1, 2);
		board.resurrectCell(2, 2);
		board.resurrectCell(3, 2);

		board.update();

		assertTrue(board.getCellStatus(2, 1));
		assertTrue(board.getCellStatus(2, 2));
		assertTrue(board.getCellStatus(2, 3));

		assertFalse(board.getCellStatus(1, 2));
		assertFalse(board.getCellStatus(3, 2));
	}

	@Test
	void testTwoCellsDie() {
		board.resurrectCell(1, 1);
		board.resurrectCell(1, 2);

		board.update();

		assertArrayEquals(board.target, new boolean[board.target.length]);
	}


	/**
	 * Verifies that values on the right edge of the board are treated as neighbors to values on the left edge of the
	 * board.
	 */
	@Test
	void testHorizontalWrapping() {
		board.resurrectCell(1, 0);
		board.resurrectCell(2, 0);
		board.resurrectCell(3, 0);

		board.update();

		assertTrue(board.getCellStatus(2, 19));
		assertTrue(board.getCellStatus(2, 0));
		assertTrue(board.getCellStatus(2, 1));

		assertFalse(board.getCellStatus(1, 0));
		assertFalse(board.getCellStatus(3, 0));
	}

	/**
	 * Verifies that values on the top edge of the board are treated as neighbors to values on the bottom edge of the
	 * board.
	 */
	@Test
	void testVerticalWrapping() {
		board.resurrectCell(9, 1);
		board.resurrectCell(9, 2);
		board.resurrectCell(9, 3);

		board.update();

		assertTrue(board.getCellStatus(8, 2));
		assertTrue(board.getCellStatus(9, 2));
		assertTrue(board.getCellStatus(0, 2));

		assertFalse(board.getCellStatus(9, 1));
		assertFalse(board.getCellStatus(9, 3));
	}

	@Test
	void testReset() {
		board.reset();
		assertArrayEquals(initialState, board.target);
	}

	@Test
	void testRandomize() {
		board.randomize();
		assertFalse(Arrays.equals(board.target, new boolean[board.target.length]));
	}
}
