package de.orchound.gameoflife.parsing;

import de.orchound.gameoflife.model.Board;
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
		assertEquals(101, board.getWidth());
		assertEquals(101, board.getHeight());

		assertTrue(board.getCellStatus(41, 45));
		assertFalse(board.getCellStatus(42, 46));
		assertTrue(board.getCellStatus(44, 48));
		assertTrue(board.getCellStatus(45, 50));
		assertTrue(board.getCellStatus(48, 50));
		assertFalse(board.getCellStatus(49, 50));
	}
}
