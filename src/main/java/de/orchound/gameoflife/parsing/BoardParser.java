package de.orchound.gameoflife.parsing;

import de.orchound.gameoflife.model.Board;

import java.nio.file.Path;

public interface BoardParser {
	Board parse(Path file);
}
