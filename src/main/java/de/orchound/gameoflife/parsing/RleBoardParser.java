package de.orchound.gameoflife.parsing;

import de.orchound.gameoflife.game.Board;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RleBoardParser implements BoardParser {

	private Board board;

	@Override
	public Board parse(File file) {
		try {
			Files.lines(file.toPath()).filter(line -> !line.startsWith("#")).forEach(this::parseLine);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return board;
	}

	private void parseLine(String line) {
		Pattern dimensionPattern = Pattern.compile("x\\s?=\\s?(\\d+),\\s?y\\s=\\s?(\\d+)");
		Matcher dimensionFinder = dimensionPattern.matcher(line);
		if (dimensionFinder.find()) {
			board = new Board(
				Integer.parseInt(dimensionFinder.group(1)),
				Integer.parseInt(dimensionFinder.group(2))
			);
		} else {
			parseBoardData(line);
		}
	}

	private void parseBoardData(String boardData) {
		String[] lines = boardData.split("\\$");
		int cellRepeat;
		boolean cellStatus;

		int rowIndex = 0;
		int cellIndex;

		for (String line : lines) {
			cellIndex = 0;
			Matcher ruleFinder = Pattern.compile("([0-9]*)([ob])").matcher(line);
			while (ruleFinder.find()) {
				cellRepeat = ruleFinder.group(1).equals("") ? 1 : Integer.parseInt(ruleFinder.group(1));
				cellStatus = !ruleFinder.group(2).equals("b");
				for (int r = 0; r < cellRepeat; r++) {
					board.target[rowIndex * board.getWidth() + cellIndex] = cellStatus;
					cellIndex++;
				}
			}
			rowIndex++;
		}
	}
}
