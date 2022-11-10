package dayseven;

import util.Solver;

import java.util.List;
import java.util.OptionalDouble;
import java.util.function.IntBinaryOperator;
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
        return calculateResult(medianOfPositions, (basePosition, position) -> Math.abs(basePosition - position));
    }

    private Integer getMedianOfPositions() {
        Integer median;
        int numberOfPositions = positions.size();
        if (numberOfPositions % 2 != 0) {
            median = positions.get((numberOfPositions + 1) / 2 - 1);
        } else {
            median = (positions.get((numberOfPositions / 2) - 1) + positions.get(numberOfPositions / 2)) / 2;
        }
        return median;
    }

    private int calculateResult(Integer basePosition, IntBinaryOperator function) {
        return positions.stream()
                .map(position -> function.applyAsInt(basePosition, position))
                .mapToInt(Integer::intValue)
                .sum();
    }

    @Override
    protected Integer solvePartTwo() {
        Integer meanOfPositions = getMeanOfPositions();
        return calculateResult(meanOfPositions, (basePosition, position) -> {
            int difference = Math.abs(basePosition - position);
            return difference * (difference + 1) / 2;
        });
    }

    private Integer getMeanOfPositions() {
        Integer mean = null;
        OptionalDouble optionalAverage = positions.stream()
                .mapToInt(Integer::intValue)
                .average();
        if (optionalAverage.isPresent()) {
            mean = (int) Math.floor(optionalAverage.getAsDouble());
        }
        return mean;
    }

}
