package de.orchound.gameoflife;

import de.orchound.gameoflife.model.Game;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import processing.core.PApplet;

import java.nio.file.Paths;

@Command(
	name = "Game of Life", mixinStandardHelpOptions = true, version = "1.0",
	description = "Conway's Game of Life."
)
public class GameOfLifeApplication implements Runnable {

	@Option(names = {"--width"}, description = "Board width", defaultValue = "100")
	private int width;

	@Option(names = {"--height"}, description = "Board height", defaultValue = "100")
	private int height;

	@Option(names = {"--rule"}, description = "Rulestring for producing generations", defaultValue = "B3/S23")
	private String ruleString;

	@Option(names = {"--pattern"}, description = "Pattern file to load")
	private String pattern;

	@Option(names = {"--renderer"}, description = "2D board renderer", defaultValue = "default")
	private String renderer;

	@Override
	public void run() {
		Game game = pattern != null ? new Game(Paths.get(pattern)) : new Game(width, height, ruleString);

		PApplet.runSketch(
			new String[] {"GameOfLifeApplication"},
			new GameOfLifeApplet(game, renderer)
		);
	}
}
