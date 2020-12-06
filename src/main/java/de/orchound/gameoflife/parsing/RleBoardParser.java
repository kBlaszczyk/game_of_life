package de.orchound.gameoflife.parsing;

import de.orchound.gameoflife.model.Board;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RleBoardParser implements BoardParser {

	private final int extraBoardSize = 80;
	private final int halfExtraBoardSize = extraBoardSize / 2;
	private final Pattern dimensionPattern = Pattern.compile("x\\s?=\\s?(\\d+),\\s?y\\s=\\s?(\\d+),\\srule\\s=\\s(B\\d+/S\\d+)");
	private final Pattern itemPattern = Pattern.compile("(\\d*)([ob$])");

	private int rowIndex = halfExtraBoardSize;
	private int cellIndex = halfExtraBoardSize;
	private Board board;

	@Override
	public Board parse(Path file) {
		try {
			Files.lines(file)
				.filter(line -> !line.startsWith("#"))
				.forEach(this::parseLine);
		} catch (IOException e) {
			throw new UncheckedIOException("Could not read file " + file, e);
		}

		board.makeCurrentStateInitial();
		return board;
	}

	private void parseLine(String line) {
		Matcher dimensionMatcher = dimensionPattern.matcher(line);
		if (dimensionMatcher.find()) {
			board = new Board(
				Integer.parseInt(dimensionMatcher.group(1)) + extraBoardSize,
				Integer.parseInt(dimensionMatcher.group(2)) + extraBoardSize,
				dimensionMatcher.group(3)
			);
		} else {
			parseBoardData(line);
		}
	}

	private void parseBoardData(String boardData) {
		Matcher itemMatcher = itemPattern.matcher(boardData);

		while (itemMatcher.find()) {
			String digitGroup = itemMatcher.group(1);
			int cellRepetition = digitGroup.isEmpty() ? 1 : Integer.parseInt(digitGroup);

			String tag = itemMatcher.group(2);
			if (tag.equals("$")) {
				rowIndex += cellRepetition;
				cellIndex = halfExtraBoardSize;
			} else {
				boolean cellStatus = tag.equals("o");
				for (int r = 0; r < cellRepetition; r++) {
					board.target[rowIndex * board.getWidth() + cellIndex] = cellStatus;
					cellIndex++;
				}
			}
		}
	}
}
