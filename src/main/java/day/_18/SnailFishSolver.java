package day._18;

import util.common.Solver;

import java.util.List;
import java.util.stream.Collectors;

public class SnailFishSolver extends Solver<Long> {

    private final List<SnailFishNumber> numbers;
    private SnailFishNumber result;

    public SnailFishSolver(String filename) {
        super(filename);
        numbers = puzzle.stream().map(SnailFishNumber::parseANumber).collect(Collectors.toList());
    }

    @Override
    protected Long solvePartOne() {
        result = numbers.get(0).add(numbers.get(1));
        result.reduce();
        for (int i = 2; i < numbers.size(); i++) {
            result = result.add(numbers.get(i));
            result.reduce();
        }
        return result.magnitude();
    }

    @Override
    protected Long solvePartTwo() {
        return null;
    }

    public SnailFishNumber getResult() {
        return result;
    }
}
