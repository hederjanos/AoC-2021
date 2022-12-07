package day._18;

import util.common.Solver;

import java.util.List;
import java.util.stream.Collectors;

public class SnailFishSolver extends Solver<Integer> {

    private List<SnailFishNumber> numbers;

    public SnailFishSolver(String filename) {
        super(filename);
        numbers = puzzle.stream().map(SnailFishNumber::parseANumber).collect(Collectors.toList());
    }

    @Override
    protected Integer solvePartOne() {
        return null;
    }

    @Override
    protected Integer solvePartTwo() {
        return null;
    }

}
