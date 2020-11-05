package de.orchound.gameoflife.parsing;

import de.orchound.gameoflife.game.Board;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class RleBoardParserTest {

	private final BoardParser boardParser = new RleBoardParser();

	@Test
	void testParse() {
		Path file = new File("src/test/resources/pattern.rle").toPath();
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
