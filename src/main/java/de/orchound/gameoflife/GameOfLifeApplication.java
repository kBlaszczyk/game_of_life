package de.orchound.gameoflife;

import de.orchound.gameoflife.model.Game;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import processing.core.PApplet;

@Command(
	name = "Game of Life", mixinStandardHelpOptions = true, version = "1.0",
	description = "Conway's Game of Life."
)
public class GameOfLifeApplication implements Runnable {

	@Option(names = {"--width"}, description = "Board width", defaultValue = "100")
	private int width;

	@Option(names = {"--height"}, description = "Board height", defaultValue = "100")
	private int height;

	@Override
	public void run() {
		PApplet.runSketch(
			new String[] {"GameOfLifeApplication"},
			new GameOfLifeApplet(new Game(width, height))
		);
	}
}
