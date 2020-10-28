package de.orchound.gameoflife;

import de.orchound.gameoflife.game.Board;
import de.orchound.gameoflife.rendering.BoardRenderer;
import processing.core.PApplet;
import processing.event.MouseEvent;

public class GameOfLifeApplet extends PApplet {

	private final Board board;
	private final BoardRenderer boardRenderer;

	private final long frameDurationIncrement = 100_000_000L;
	private long gameTimeAccumulator = 0L;
	private long gameFrameTime = 200_000_000L;
	private long previousTimestamp = System.nanoTime();
	private boolean paused = false;

	private final int windowWidth = 1280;
	private final int windowHeight = 720;
	private float viewOffsetX = windowWidth / 2f;
	private float viewOffsetY = windowHeight / 2f;

	private float scale;
	private final float minScale;
	private final float maxScale;

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
		noSmooth();
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
		case ' ' -> togglePauseSimulation();
		case '+' -> changeSpeed(gameFrameTime - frameDurationIncrement);
		case '-' -> changeSpeed(gameFrameTime + frameDurationIncrement);
		case 'c' -> resetView();
		}
	}

	@Override
	public void mouseWheel(MouseEvent event) {
		scale = constrain(scale - event.getCount() * 0.5f, minScale, maxScale);
	}

	private void update() {
		board.update();
		boardRenderer.update();
	}

	private void tick() {
		long currentTimestamp = System.nanoTime();
		gameTimeAccumulator += paused ? 0L : currentTimestamp - previousTimestamp;
		previousTimestamp = currentTimestamp;

		while (gameTimeAccumulator >= gameFrameTime) {
			update();
			gameTimeAccumulator -= gameFrameTime;
		}
	}

	private void resetView() {
		viewOffsetX = windowWidth / 2f;
		viewOffsetY = windowHeight / 2f;
		scale = getInitialScale(board.width, board.height, windowWidth, windowHeight);
	}

	private void togglePauseSimulation() {
		if (paused) {
			paused = false;
		} else {
			gameTimeAccumulator = 0L;
			paused = true;
		}
	}

	private void changeSpeed(long target) {
		gameFrameTime = Math.max(50_000_000L, Math.min(1_000_000_000L, target));
	}

	private float getInitialScale(int boardWidth, int boardHeight, int screenWidth, int screenHeight) {
		return min((float) screenWidth / boardWidth, (float) screenHeight / boardHeight);
	}
}
