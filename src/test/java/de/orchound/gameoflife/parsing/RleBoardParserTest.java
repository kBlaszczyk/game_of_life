package de.orchound.gameoflife.parsing;

import de.orchound.gameoflife.game.Board;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class RleBoardParserTest {

	@Test
	void testParse() {
		File file = new File("src/test/resources/pattern.rle");
		BoardParser boardParser = new RleBoardParser();
		Board board = boardParser.parse(file);
		assertEquals(10, board.getWidth());
		assertEquals(9, board.getHeight());
		assertFalse(board.getCellStatus(0, 0));
		assertFalse(board.getCellStatus(8, 9));
		assertFalse(board.getCellStatus(4, 4));
		assertTrue(board.getCellStatus(4, 1));
		assertTrue(board.getCellStatus(2, 3));
	}
}