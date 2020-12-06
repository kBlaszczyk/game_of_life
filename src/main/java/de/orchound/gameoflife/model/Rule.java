package de.orchound.gameoflife.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Rule {
	List<Integer> birth = new ArrayList<>();
	List<Integer> survival = new ArrayList<>();

	public Rule(String ruleString) {
		parseRulestring(ruleString);
	}

	public boolean determineCellStatus(boolean cell, int livingNeighborsCount) {

		if (cell) {
			for (int x : survival) {
				if (livingNeighborsCount == x)
					return true;
			}
		} else {
			for (int x: birth) {
				if (livingNeighborsCount == x)
					return true;
			}
		}
		return false;
	}

	private void parseRulestring(String ruleString) {
		Pattern pattern = Pattern.compile("B(\\d+)/S(\\d+)");
		Matcher matcher = pattern.matcher(ruleString);
		if (matcher.find()) {
			Arrays.stream(matcher.group(1).split("")).forEach(x -> birth.add(Integer.parseInt(x)));
			Arrays.stream(matcher.group(2).split("")).forEach(x -> survival.add(Integer.parseInt(x)));
		}
	}
}
