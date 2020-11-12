package de.orchound.gameoflife.view;

import java.util.HashMap;
import java.util.Map;

public class BoardRendererRegistry {

	BoardRenderer defaultRenderer = new DefaultBoardRenderer();
	Map<String, BoardRenderer> nameToRenderer = new HashMap<>();

	public BoardRendererRegistry() {
		nameToRenderer.put("trail", new TrailBoardRenderer());
	}

	public BoardRenderer getRenderer(String name) {
		return nameToRenderer.getOrDefault(name, defaultRenderer);
	}
}
