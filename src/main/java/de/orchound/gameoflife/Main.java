package de.orchound.gameoflife;

import picocli.CommandLine;

public class Main {

	public static void main(String[] args) {
		new CommandLine(new GameOfLifeApplication()).execute(args);
	}
}
