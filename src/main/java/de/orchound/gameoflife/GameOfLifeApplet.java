package de.orchound.gameoflife;

import de.orchound.gameoflife.model.Game;
import de.orchound.gameoflife.processing.Button;
import de.orchound.gameoflife.processing.LabeledButton;
import de.orchound.gameoflife.processing.PauseButton;
import de.orchound.gameoflife.processing.Slider;
import de.orchound.gameoflife.processing.ui.UIElementContainer;
import de.orchound.gameoflife.view.BoardRenderer;
import de.orchound.gameoflife.view.BoardRendererRegistry;
import de.orchound.gameoflife.view.BoardView;
import org.joml.Vector2i;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.event.MouseEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GameOfLifeApplet extends PApplet {

	private final Game game;
	private final BoardRenderer boardRenderer;

	private final Vector2i windowSize = new Vector2i(1280, 720);

	private BoardView boardView;
	private final UIElementContainer buttonsContainer = new UIElementContainer(10, 10, this);
	private final List<Button> buttons = new ArrayList<>();
	private PauseButton pauseButton;
	private Slider speedSlider;

	private final MouseInputEvent mouseInputEvent = new MouseInputEvent();

	public GameOfLifeApplet(Game game, String renderer) {
		this.game = game;
		this.boardRenderer = new BoardRendererRegistry().getRenderer(renderer);
	}

	@Override
	public void setup() {
		surface.setTitle("Game of Life");
		surface.setResizable(true);
		stroke(255);
		textAlign(PConstants.CENTER);
		textSize(20);

		boardView = new BoardView(game, this, boardRenderer);

		speedSlider = new Slider(100, 300, this, game::setSpeed);
		pauseButton = new PauseButton(10, 300, this, game::togglePause);
		game.registerPauseObserver(pauseButton::setPaused);

		buttons.addAll(Arrays.asList(
			new LabeledButton("Reset", this, game::resetBoard),
			new LabeledButton("Randomize", this, game::randomize),
			new LabeledButton("Center View", this, boardView::reset),
			new LabeledButton("Clear", this, game::clear),
			new LabeledButton("Save", this, game::save)
		));

		buttons.forEach(buttonsContainer::addElement);
		buttons.add(pauseButton);
	}

	@Override
	public void settings() {
		size(windowSize.x, windowSize.y);
		noSmooth();
	}

	@Override
	public void draw() {
		processInput();
		game.update();

		background(0);
		boardView.draw();

		drawHud();
	}

	private void processInput() {
		mouseInputEvent.setMousePosition(mouseX, mouseY);
		mouseInputEvent.setPreviousMousePosition(pmouseX, pmouseY);

		speedSlider.handleMouseInput(mouseInputEvent);
		if (!mouseInputEvent.isConsumed())
			boardView.handleMouseInput(mouseInputEvent);

		for (Button button : buttons) {
			if (mouseInputEvent.isClicked() && mouseInputEvent.getLeftKey())
				button.click(mouseX, mouseY);
		}

		mouseInputEvent.reset();
	}

	private void drawHud() {
		buttonsContainer.draw();
		pauseButton.draw();
		speedSlider.draw();
	}

	@Override
	public void mouseDragged() {
		mouseInputEvent.setDragged();
		updatePressedMouseButtons();
	}

	@Override
	public void mousePressed() {
		mouseInputEvent.setPressed();
		updatePressedMouseButtons();
	}

	@Override
	public void mouseReleased() {
		mouseInputEvent.setReleased();
		updatePressedMouseButtons();
	}

	@Override
	public void mouseClicked() {
		mouseInputEvent.setClicked();
		updatePressedMouseButtons();
	}

	@Override
	public void mouseWheel(MouseEvent event) {
		mouseInputEvent.setScrolled(event.getCount());
	}

	private void updatePressedMouseButtons() {
		switch (mouseButton) {
		case LEFT -> mouseInputEvent.setLeftKey();
		case RIGHT -> mouseInputEvent.setRightKey();
		case CENTER -> mouseInputEvent.setMiddleKey();
		}
	}

	@Override
	public void keyPressed() {
		switch (key) {
		case ' ' -> game.togglePause();
		case 'c' -> boardView.reset();
		case 'r' -> game.resetBoard();
		case 'q' -> game.randomize();
		case 's' -> game.save();
		case BACKSPACE -> game.clear();
		}
	}
}
