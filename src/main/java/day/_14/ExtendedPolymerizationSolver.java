package day._14;

import util.common.Solver;

import java.util.HashMap;
import java.util.Map;

public class ExtendedPolymerizationSolver extends Solver<Integer> {

    private String template;
    private final Map<String, Character> insertionRules = new HashMap<>();
    private Map<Character, Integer> characterMap;

    public ExtendedPolymerizationSolver(String filename) {
        super(filename);
        parseInput();
    }

    private void parseInput() {
        template = puzzle.get(0);
        int i = 2;
        while (i < puzzle.size()) {
            String[] rule = puzzle.get(i).split(" -> ");
            insertionRules.putIfAbsent(rule[0], rule[1].charAt(0));
            i++;
        }
    }

    @Override
    protected Integer solvePartOne() {
        int i = 0;
        while (i < 10) {
            step();
            i++;
        }
        characterMap = countCharacters();
        return getMostCommonCharacterCount() - getLeastCommonCharacterCount();
    }

    private void step() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < template.length() - 1; i++) {
            String current = template.substring(i, i + 2);
            Character insertion = null;
            if (insertionRules.containsKey(current)) {
                insertion = insertionRules.get(current);
            }
            if (i == 0) {
                stringBuilder.append(current.charAt(0));
            }
            if (insertion != null) {
                stringBuilder.append(insertion);
            }
            stringBuilder.append(current.charAt(1));
        }
        template = stringBuilder.toString();
    }

    private Map<Character, Integer> countCharacters() {
        Map<Character, Integer> characterMap = new HashMap<>();
        for (int i = 0; i < template.length(); i++) {
            if (!characterMap.containsKey(template.charAt(i))) {
                characterMap.put(template.charAt(i), 1);
            } else {
                characterMap.put(template.charAt(i), characterMap.get(template.charAt(i)) + 1);
            }
        }
        return characterMap;
    }

    private Integer getMostCommonCharacterCount() {
        return characterMap.values().stream().max(Integer::compareTo).orElse(null);
    }

    private Integer getLeastCommonCharacterCount() {
        return characterMap.values().stream().min(Integer::compareTo).orElse(null);
    }

    @Override
    protected Integer solvePartTwo() {
        int i = 0;
        while (i < 40) {
            step();
            i++;
        }
        characterMap = countCharacters();
        return getMostCommonCharacterCount() - getLeastCommonCharacterCount();
    }

}
