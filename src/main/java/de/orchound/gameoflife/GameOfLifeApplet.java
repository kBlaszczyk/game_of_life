package de.orchound.gameoflife;

import de.orchound.gameoflife.game.Board;
import de.orchound.gameoflife.rendering.BoardRenderer;
import processing.core.PApplet;
import processing.event.MouseEvent;

public class GameOfLifeApplet extends PApplet {

	private final Board board;
	private final BoardRenderer boardRenderer;

	private final long frameDurationIncrement = 100_000_000L;
	private long frameDuration = 200_000_000L;
	private long updateTimestamp = System.nanoTime();

	private final int windowWidth = 1280;
	private final int windowHeight = 720;
	private float viewOffsetX = windowWidth / 2f;
	private float viewOffsetY = windowHeight / 2f;

	private float scale;
	private float minScale;
	private float maxScale;

	public GameOfLifeApplet(Board board) {
		this.board = board;
 		this.boardRenderer = new BoardRenderer(board, this);
		maxScale = 40f;
		scale = min(maxScale, getInitialScale(board.width, board.height, windowWidth, windowHeight));
		minScale = min(1f, scale);
	}

	@Override
	public void setup() {
		stroke(255);
	}

	@Override
	public void settings() {
		size(windowWidth, windowHeight);
	}

	@Override
	public void draw() {
		tick();

		background(0);

		pushMatrix();

		translate(viewOffsetX, viewOffsetY);
		scale(scale);
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
		case '+' -> changeSpeed(frameDuration - frameDurationIncrement);
		case '-' -> changeSpeed(frameDuration + frameDurationIncrement);
		case 'c' -> resetView();
		}
	}

	@Override
	public void mouseWheel(MouseEvent event) {
		scale = constrain(scale - event.getCount() * 0.5f, minScale, maxScale);
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

	private void resetView() {
		viewOffsetX = windowWidth / 2f;
		viewOffsetY = windowHeight / 2f;
		scale = getInitialScale(board.width, board.height, windowWidth, windowHeight);
	}

	private void changeSpeed(long target) {
		frameDuration = Math.max(100_000_000L, Math.min(1_000_000_000L, target));
	}

	private float getInitialScale(int boardWidth, int boardHeight, int screenWidth, int screenHeight) {
		return min((float) screenWidth / boardWidth, (float) screenHeight / boardHeight);
	}
}
