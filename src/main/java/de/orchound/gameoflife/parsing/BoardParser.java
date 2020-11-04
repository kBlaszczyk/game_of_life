package de.orchound.gameoflife.parsing;

import de.orchound.gameoflife.game.Board;

import java.io.File;
import java.io.IOException;

public interface BoardParser {
	Board parse(File file);
}
