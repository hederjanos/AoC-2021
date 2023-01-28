package day._14;

import util.common.Solver;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ExtendedPolymerizationSolver extends Solver<Long> {

    private String template;
    private final Map<String, Character> insertionRules = new HashMap<>();
    private Map<String, Long> currentPairs;

    public ExtendedPolymerizationSolver(String filename) {
        super(filename);
        parseInput();
    }

    private void parseInput() {
        template = puzzle.get(0);
        IntStream.range(2, puzzle.size()).forEach(i -> {
            String[] rule = puzzle.get(i).split(" -> ");
            insertionRules.putIfAbsent(rule[0], rule[1].charAt(0));
        });
    }

    private void initializePairs() {
        currentPairs = IntStream.range(0, template.length() - 1)
                .mapToObj(i -> template.substring(i, i + 2))
                .collect(Collectors.toMap(pair -> pair, pair -> 1L, (curr, next) -> curr + 1));
    }

    @Override
    protected Long solvePartOne() {
        initializePairs();
        solve(10);
        return calculateResult();
    }

    private void solve(int steps) {
        IntStream.range(0, steps).forEach(i -> solveOneStep());
    }

    private void solveOneStep() {
        Map<String, Long> newPairs = new HashMap<>();
        currentPairs.entrySet().stream()
                .filter(entry -> insertionRules.containsKey(entry.getKey()))
                .forEach(entry -> updateNewPairs(newPairs, entry));
        currentPairs = newPairs;
    }

    private void updateNewPairs(Map<String, Long> newPairs, Map.Entry<String, Long> entry) {
        String fractionOne = entry.getKey().charAt(0) + insertionRules.get(entry.getKey()).toString();
        long countOfFractionOne = newPairs.getOrDefault(fractionOne, 0L);
        newPairs.compute(fractionOne, (k, v) -> v == null ? entry.getValue() : countOfFractionOne + entry.getValue());
        String fractionTwo = insertionRules.get(entry.getKey()).toString() + entry.getKey().charAt(1);
        long countOfFractionTwo = newPairs.getOrDefault(fractionTwo, 0L);
        newPairs.compute(fractionTwo, (k, v) -> v == null ? entry.getValue() : countOfFractionTwo + entry.getValue());
    }

    private long calculateResult() {
        Map<String, Long> characterMap = countCharacters();
        return getMostCommonCharacterCount(characterMap) - getLeastCommonCharacterCount(characterMap);
    }

    private Map<String, Long> countCharacters() {
        Map<String, Long> characterMap = currentPairs.entrySet().stream()
                .collect(Collectors.toMap(entry -> String.valueOf(entry.getKey().charAt(0)), Map.Entry::getValue, Long::sum));
        characterMap.compute(String.valueOf(template.charAt(template.length() - 1)), (k, v) -> v == null ? 1L : v + 1L);
        return characterMap;
    }

    private Long getMostCommonCharacterCount(Map<String, Long> characterMap) {
        return characterMap.values().stream().max(Long::compareTo).orElse(null);
    }

    private Long getLeastCommonCharacterCount(Map<String, Long> characterMap) {
        return characterMap.values().stream().min(Long::compareTo).orElse(null);
    }

    @Override
    protected Long solvePartTwo() {
        initializePairs();
        solve(40);
        return calculateResult();
    }

}
