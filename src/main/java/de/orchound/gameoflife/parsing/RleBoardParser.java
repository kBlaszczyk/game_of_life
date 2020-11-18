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
	private Board board;
	Pattern dimensionPattern = Pattern.compile("x\\s?=\\s?(\\d+),\\s?y\\s=\\s?(\\d+)");
	Pattern rulePattern = Pattern.compile("([0-9]*)([ob])");
	int rowIndex = halfExtraBoardSize;

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
				Integer.parseInt(dimensionMatcher.group(2)) + extraBoardSize
			);
		} else {
			parseBoardData(line);
		}
	}

	private void parseBoardData(String boardData) {
		String[] lines = boardData.split("\\$");
		int cellRepeat;
		boolean cellStatus;

		for (String line : lines) {
			int cellIndex = halfExtraBoardSize;
			Matcher ruleMatcher = rulePattern.matcher(line);
			while (ruleMatcher.find()) {
				String digitGroup = ruleMatcher.group(1);
				cellRepeat = digitGroup.isEmpty() ? 1 : Integer.parseInt(digitGroup);
				cellStatus = ruleMatcher.group(2).equals("o");
				for (int r = 0; r < cellRepeat; r++) {
					board.target[rowIndex * board.getWidth() + cellIndex] = cellStatus;
					cellIndex++;
				}
			}
			rowIndex++;
		}
	}
}
