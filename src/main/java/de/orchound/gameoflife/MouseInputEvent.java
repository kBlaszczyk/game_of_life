package de.orchound.gameoflife;

public class MouseInputEvent {

	private static final int MOUSE1 = 1;
	private static final  int MOUSE2 = 2;
	private static final int MOUSE3 = 4;

	private static final int CLICKED = 1;
	private static final int PRESSED = 2;
	private static final int RELEASED = 4;
	private static final int DRAGGED = 8;

	private int mouseX = 0;
	private int mouseY = 0;
	private int previousMouseX = 0;
	private int previousMouseY = 0;

	private int keys = 0;
	private int actions = 0;

	private boolean consumed = false;

	public void consume() {
		consumed = true;
	}

	public boolean isConsumed() {
		return consumed;
	}

	public void reset() {
		keys = 0;
		actions = 0;
		consumed = false;
	}

	public int getActions() {
		return actions;
	}

	public int getKeys() {
		return keys;
	}

	public void addAction(int action) {
		actions |= action;
	}

	public void addKey(int key) {
		keys |= key;
	}

	public int getMouseX() {
		return mouseX;
	}

	public int getMouseY() {
		return mouseY;
	}

	public void setMousePosition(int x, int y) {
		mouseX = x;
		mouseY = y;
	}

	public int getPreviousMouseX() {
		return previousMouseX;
	}

	public int getPreviousMouseY() {
		return previousMouseY;
	}

	public void setPreviousMousePosition(int x, int y) {
		previousMouseX = x;
		previousMouseY = y;
	}

	public boolean wasClicked() {
		return getActionState(CLICKED);
	}

	public void setClicked() {
		actions |= CLICKED;
	}

	public boolean wasPressed() {
		return getActionState(PRESSED);
	}

	public void setPressed() {
		actions |= PRESSED;
	}

	public boolean wasDragged() {
		return getActionState(DRAGGED);
	}

	public void setDragged() {
		actions |= DRAGGED;
	}

	public boolean getActionState(int action) {
		return (actions & action) != 0;
	}

	public boolean getLeftKey() {
		return getKeyState(MOUSE1);
	}

	public void setLeftKey() {
		keys |= MOUSE1;
	}

	public boolean getRightKey() {
		return getKeyState(MOUSE2);
	}

	public void setRightKey() {
		keys |= MOUSE1;
	}

	public boolean getMiddleKey() {
		return getKeyState(MOUSE3);
	}

	public void setMiddleKey() {
		keys |= MOUSE3;
	}

	private boolean getKeyState(int key) {
		return (keys & key) != 0;
	}
}
