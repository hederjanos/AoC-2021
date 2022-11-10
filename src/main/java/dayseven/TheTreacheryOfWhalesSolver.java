package dayseven;

import util.Solver;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TheTreacheryOfWhalesSolver extends Solver<Integer> {

    private final List<Integer> positions;

    protected TheTreacheryOfWhalesSolver(String filename) {
        super(filename);
        positions = parseInput();
    }

    private List<Integer> parseInput() {
        return this.puzzle.stream()
                .flatMap(line -> Stream.of(line.split(",")))
                .map(Integer::parseInt)
                .sorted()
                .collect(Collectors.toList());
    }

    @Override
    protected Integer solvePartOne() {
        Integer medianOfPositions = getMedianOfPositions();
        return positions.stream()
                .map(position -> Math.abs(position - medianOfPositions))
                .mapToInt(Integer::intValue)
                .sum();
    }

    public Integer getMedianOfPositions() {
        Integer median;
        int numberOfPositions = positions.size();
        if (numberOfPositions % 2 != 0) {
            median = positions.get((numberOfPositions + 1) / 2 - 1);
        } else {
            median = (positions.get((numberOfPositions / 2) - 1) + positions.get(numberOfPositions / 2)) / 2;
        }
        return median;
    }

    @Override
    protected Integer solvePartTwo() {
        return null;
    }

}
