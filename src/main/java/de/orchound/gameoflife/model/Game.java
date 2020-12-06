package de.orchound.gameoflife.model;

import de.orchound.gameoflife.parsing.RleBoardParser;
import org.joml.Vector2ic;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Consumer;

public class Game {

	private final Board board;
	private final Random random = new Random();

	private long gameTimeAccumulator = 0L;

	private final long minFrameTime = 50_000_000L;
	private final long maxFrameTime = 1_000_000_000L;
	private long frameTime = minFrameTime;
	private long previousTimestamp = System.nanoTime();

	private boolean paused = true;

	private final List<Consumer<boolean[]>> boardDataObservers = new ArrayList<>();
	private final List<Consumer<Boolean>> pauseObservers = new ArrayList<>();

	public Game(int width, int height, String ruleString) {
		board = new Board(width, height, ruleString);
		randomize();
	}

	public Game(Path file) {
		RleBoardParser rleBoardParser = new RleBoardParser();
		board = rleBoardParser.parse(file);
	}

	public void update() {
		long currentTimestamp = System.nanoTime();
		gameTimeAccumulator += paused ? 0L : currentTimestamp - previousTimestamp;
		previousTimestamp = currentTimestamp;

		while (gameTimeAccumulator >= frameTime) {
			tick();
			gameTimeAccumulator -= frameTime;
		}
	}

	private void tick() {
		board.update();
		for (Consumer<boolean[]> observer : boardDataObservers) {
			observer.accept(board.target);
		}
	}

	public void togglePause() {
		paused = !paused;
		if (paused)
			gameTimeAccumulator = 0;

		for (Consumer<Boolean> observer : pauseObservers) {
			observer.accept(paused);
		}
	}

	/**
	 * Sets the animation speed depending on the specified value.
	 * The value is expected to be in the closed interval [0..1].
	 * Where 0 and 1 represent the slowest and highest animation speed respectively.
	 * @param value speed indicator [0, 1]
	 */
	public void setSpeed(float value) {
		float speed = Math.max(0f, Math.min(1f, value));
		frameTime = maxFrameTime - (long) (speed * (maxFrameTime - minFrameTime));
	}

	public void toggleCell(Vector2ic cell) {
		if (!cellInBoardRange(cell))
			return;

		if (getCellStatus(cell)) {
			board.killCell(cell.y(), cell.x());
		} else {
			board.resurrectCell(cell.y(), cell.x());
		}
		notifyBoardDataObservers();
	}

	public void setCell(Vector2ic cell, boolean resurrect) {
		if (cellInBoardRange(cell) && getCellStatus(cell) != resurrect) {
			if (resurrect) {
				board.resurrectCell(cell.y(), cell.x());
			} else {
				board.killCell(cell.y(), cell.x());
			}
			notifyBoardDataObservers();
		}
	}

	public boolean getCellStatus(Vector2ic cell) {
		return cellInBoardRange(cell) && board.getCellStatus(cell.y(), cell.x());
	}

	private boolean cellInBoardRange(Vector2ic cell) {
		return Math.min(cell.x(), cell.y()) >= 0
			&& cell.x() < board.getWidth()
			&& cell.y() < board.getHeight();
	}

	public void resetBoard() {
		board.reset();
		gameTimeAccumulator = 0;
		notifyBoardDataObservers();
	}

	public void randomize() {
		for (int i = 0; i < board.target.length; i++) {
			board.target[i] = random.nextBoolean();
		}

		board.makeCurrentStateInitial();
		gameTimeAccumulator = 0;
		notifyBoardDataObservers();
	}

	public void clear() {
		board.clear();
		gameTimeAccumulator = 0;
		notifyBoardDataObservers();
	}

	public void save() {
		board.makeCurrentStateInitial();
	}

	public void registerBoardDataObserver(Consumer<boolean[]> observer) {
		boardDataObservers.add(observer);
		observer.accept(board.target);
	}

	public void notifyBoardDataObservers() {
		for (Consumer<boolean[]> observer : boardDataObservers) {
			observer.accept(board.target);
		}
	}

	public void registerPauseObserver(Consumer<Boolean> observer) {
		pauseObservers.add(observer);
	}

	public Vector2ic getSize() {
		return board.size;
	}
}
