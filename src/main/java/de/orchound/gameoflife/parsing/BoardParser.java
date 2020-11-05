package de.orchound.gameoflife.parsing;

import de.orchound.gameoflife.game.Board;

import java.io.File;
import java.nio.file.Path;

public interface BoardParser {
	Board parse(Path file);
}
