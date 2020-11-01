package de.orchound.gameoflife;

import de.orchound.gameoflife.game.Board;
import de.orchound.gameoflife.rendering.BoardRenderer;
import org.joml.Vector2f;
import org.joml.Vector2i;
import org.joml.Vector2ic;
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

	private final Vector2i windowSize = new Vector2i(1280, 720);
	private final Vector2f viewOffset = new Vector2f(windowSize).div(2f);

	private float scale;
	private final float minScale;
	private final float maxScale;

	private final Vector2f bufferVector2f = new Vector2f();
	private final Vector2i bufferVector2i = new Vector2i();

	public GameOfLifeApplet(Board board) {
		this.board = board;

		boardRenderer = new BoardRenderer(board, this);
 		boardRenderer.update();

 		maxScale = 40f;
		scale = min(maxScale, getInitialScale());
		minScale = min(1f, scale);
	}

	@Override
	public void setup() {
		surface.setTitle("Game of Life");
		surface.setResizable(true);
		stroke(255);
	}

	@Override
	public void settings() {
		size(windowSize.x, windowSize.y);
		noSmooth();
	}

	@Override
	public void draw() {
		tick();

		checkWindowSize();

		background(0);

		pushMatrix();

		translate(viewOffset.x, viewOffset.y);
		scale(scale);
		boardRenderer.render();

		popMatrix();
	}

	@Override
	public void mouseDragged() {
		if (mouseButton == LEFT) {
			viewOffset.add(mouseX - pmouseX, mouseY - pmouseY);
		}
	}

	@Override
	public void mousePressed() {
		Vector2f position = bufferVector2f.set(mouseX, mouseY)
			.sub(viewOffset)
			.div(scale);
		Vector2i cell = boardRenderer.getCellAt(position, bufferVector2i);

		if (cellInBoardRange(cell)) {
			board.resurrectCell(cell.y, cell.x);
			boardRenderer.update();
		}
	}

	private boolean cellInBoardRange(Vector2ic cell) {
		return Math.min(cell.x(), cell.y()) >= 0
			&& cell.x() < board.getWidth()
			&& cell.y() < board.getHeight();
	}

	@Override
	public void keyPressed() {
		switch (key) {
		case ' ' -> togglePauseSimulation();
		case '+' -> changeSpeed(gameFrameTime - frameDurationIncrement);
		case '-' -> changeSpeed(gameFrameTime + frameDurationIncrement);
		case 'c' -> resetView();
		case 'r' -> resetBoard();
		case 'q' -> randomize();
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
		viewOffset.set(windowSize).div(2f);
		scale = getInitialScale();
	}

	private void checkWindowSize() {
		if (windowSize.x != width || windowSize.y != height) {
			windowSize.set(width, height);
		}
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

	private float getInitialScale() {
		return min((float) windowSize.x / board.getWidth(), (float) windowSize.y / board.getHeight());
	}

	private void resetBoard() {
		board.reset();
		boardRenderer.update();
		gameTimeAccumulator = 0;
	}

	private void randomize() {
		board.randomize();
		boardRenderer.update();
		gameTimeAccumulator = 0;
	}
}
