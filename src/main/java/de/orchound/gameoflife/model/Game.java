package de.orchound.gameoflife.model;

import org.joml.Vector2ic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.function.Consumer;

public class Game {

	private final Board board;
	private final Random random = new Random();

	private long gameTimeAccumulator = 0L;
	private long frameTime = 200_000_000L;
	private long previousTimestamp = System.nanoTime();

	private boolean paused = true;

	private final List<Consumer<boolean[]>> boardDataObservers = new ArrayList<>();
	private final List<Consumer<Boolean>> pauseObservers = new ArrayList<>();

	private boolean paintMode;

	public Game(int width, int height) {
		board = new Board(width, height);
		randomize();
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

	public long getFrameTime() {
		return frameTime;
	}

	public void setFrameTime(long target) {
		frameTime = Math.max(50_000_000L, Math.min(1_000_000_000L, target));
	}

	public void toggleCell(Vector2ic cell) {
		if (!cellInBoardRange(cell))
			return;

		if (board.getCellStatus(cell.y(), cell.x())) {
			board.killCell(cell.y(), cell.x());
			paintMode = false;
		}
		else {
			board.resurrectCell(cell.y(), cell.x());
			paintMode = true;
		}
		notifyBoardDataObservers();


	}

	public void setCell(Vector2ic cell) {
		if (paintMode) {
			if (!board.getCellStatus(cell.y(), cell.x())) {
				board.resurrectCell(cell.y(), cell.x());
			}
		} else {
			if (board.getCellStatus(cell.y(), cell.x())) {
				board.killCell(cell.y(), cell.x());
			}
		}
		notifyBoardDataObservers();
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
		Arrays.fill(board.target, false);
		board.makeCurrentStateInitial();
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
