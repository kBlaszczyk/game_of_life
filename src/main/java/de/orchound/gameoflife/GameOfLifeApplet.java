package de.orchound.gameoflife;

import de.orchound.gameoflife.model.Game;
import de.orchound.gameoflife.processing.Button;
import de.orchound.gameoflife.processing.LabeledButton;
import de.orchound.gameoflife.processing.PauseButton;
import de.orchound.gameoflife.view.BoardView;
import org.joml.Vector2f;
import org.joml.Vector2i;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.event.MouseEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GameOfLifeApplet extends PApplet {

	private final Game game;
	private final BoardView boardView;

	private final long frameTimeIncrement = 100_000_000L;

	private final Vector2i windowSize = new Vector2i(1280, 720);
	private final Vector2f viewOffset = new Vector2f(windowSize).div(2f);

	private float scale;
	private final float minScale;
	private final float maxScale;

	private final List<Button> buttons = new ArrayList<>();

	private final Vector2f bufferVector2f = new Vector2f();
	private final Vector2i bufferVector2i = new Vector2i();

	public GameOfLifeApplet(Game game) {
		this.game = game;

		boardView = new BoardView(game, this);

		maxScale = 40f;
		scale = min(maxScale, getInitialScale());
		minScale = min(1f, scale);
	}

	@Override
	public void setup() {
		surface.setTitle("Game of Life");
		surface.setResizable(true);
		stroke(255);
		textAlign(PConstants.CENTER);
		textSize(20);

		PauseButton pauseButton = new PauseButton(10, 10, this, game::togglePause);
		game.registerPauseObserver(pauseButton::setPaused);

		buttons.addAll(Arrays.asList(
			pauseButton,
			new LabeledButton(10, 70, "Reset", this, game::resetBoard),
			new LabeledButton(10, 100, "Randomize", this, game::randomize),
			new LabeledButton(10, 130, "Center View", this, this::resetView)
		));
	}

	@Override
	public void settings() {
		size(windowSize.x, windowSize.y);
		noSmooth();
	}

	@Override
	public void draw() {
		game.update();

		checkWindowSize();

		background(0);

		drawGame();
		drawHud();
	}

	private void drawGame() {
		pushMatrix();

		translate(viewOffset.x, viewOffset.y);
		scale(scale);
		boardView.draw();

		popMatrix();
	}

	private void drawHud() {
		buttons.forEach(Button::draw);
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

		Vector2i cell = boardView.getCellAt(position, bufferVector2i);
		game.toggleCell(cell);
	}

	@Override
	public void mouseClicked() {
		for (Button button : buttons) {
			button.click(mouseX, mouseY);
		}
	}

	@Override
	public void keyPressed() {
		switch (key) {
		case ' ' -> game.togglePause();
		case '+' -> increaseSpeed();
		case '-' -> decreaseSpeed();
		case 'c' -> resetView();
		case 'r' -> game.resetBoard();
		case 'q' -> game.randomize();
		}
	}

	@Override
	public void mouseWheel(MouseEvent event) {
		scale = constrain(scale - event.getCount() * 0.5f, minScale, maxScale);
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

	private void increaseSpeed() {
		game.setFrameTime(game.getFrameTime() - frameTimeIncrement);
	}

	private void decreaseSpeed() {
		game.setFrameTime(game.getFrameTime() + frameTimeIncrement);
	}

	private float getInitialScale() {
		return min((float) windowSize.x / boardView.size.x(), (float) windowSize.y / boardView.size.y());
	}
}
