package de.orchound.gameoflife.game;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {

	Board board = new Board(5, 5);

	@BeforeEach
	void setUp() {
		for (int i = 0; i < board.target.length; i++) {
			for (int j = 0; j < board.target[0].length; j++) {
				board.target[i][j] = false;
			}
		}
	}

	@Test
	void testSetCellAlive() {
		board.setCellAlive(1, 0);
		assertTrue(board.target[1][0]);
	}

	@Test
	void testUpdate() {
		board.setCellAlive(1, 2);
		board.setCellAlive(2, 2);
		board.setCellAlive(3, 2);

		board.update();

		assertTrue(board.target[2][1]);
		assertTrue(board.target[2][2]);
		assertTrue(board.target[2][3]);

		assertFalse(board.target[1][2]);
		assertFalse(board.target[3][2]);
	}

	@Test
	void testHorizontalWrapping() {
		board.setCellAlive(1, 0);
		board.setCellAlive(2, 0);
		board.setCellAlive(3, 0);

		board.update();

		assertTrue(board.target[2][4]);
		assertTrue(board.target[2][0]);
		assertTrue(board.target[2][1]);

		assertFalse(board.target[1][0]);
		assertFalse(board.target[3][0]);

		/*
		* verify that the right edge is treated as a neighbor to the left edge
		*/
	}
}
