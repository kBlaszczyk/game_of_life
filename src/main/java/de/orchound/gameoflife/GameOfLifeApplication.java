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

	@Option(names = {"--pattern"}, description = "Pattern file to load")
	private String pattern;

	@Override
	public void run() {
		if (pattern != null) {
			PApplet.runSketch(
				new String[] {"GameOfLifeApplication"},
				new GameOfLifeApplet(new Game(Paths.get(pattern)))
			);
		} else {
			PApplet.runSketch(
				new String[] {"GameOfLifeApplication"},
				new GameOfLifeApplet(new Game(width, height))
			);
		}
	}
}
