package de.orchound.gameoflife;

import de.orchound.gameoflife.game.Board;
import de.orchound.gameoflife.rendering.BoardRenderer;
import processing.core.PApplet;

public class GameOfLifeApplet extends PApplet {

	private final Board board;
	private final BoardRenderer boardRenderer;

	private final long frameDurationIncrement = 100_000_000L;
	private long frameDuration = 200_000_000L;
	private long updateTimestamp = System.nanoTime();

	private int viewOffsetX = 0;
	private int viewOffsetY = 0;

	public GameOfLifeApplet(Board board) {
		this.board = board;
 		this.boardRenderer = new BoardRenderer(board, this);
	}

	@Override
	public void setup() {
		stroke(255);
	}

	@Override
	public void settings() {
		size(1280, 720);
	}

	@Override
	public void draw() {
		tick();

		background(0);

		pushMatrix();

		translate(viewOffsetX, viewOffsetY);
		scale(20f);
		boardRenderer.render();

		popMatrix();
	}

	@Override
	public void mouseDragged() {
		if (mouseButton == LEFT) {
			viewOffsetX += mouseX - pmouseX;
			viewOffsetY += mouseY - pmouseY;
		}
	}

	@Override
	public void keyPressed() {
		switch (key) {
		case '+' -> increaseSpeed();
		case '-' -> decreaseSpeed();
		}
	}

	private void update() {
		board.update();
	}

	private void tick() {
		long currentTimestamp = System.nanoTime();
		if (currentTimestamp - updateTimestamp > frameDuration) {
			update();
			updateTimestamp = currentTimestamp;
		}
	}

	private void increaseSpeed() {
		frameDuration = Math.max(frameDuration - frameDurationIncrement, 100_000_000L);
	}

	private void decreaseSpeed() {
		frameDuration = Math.min(frameDuration + frameDurationIncrement, 1_000_000_000L);
	}
}
