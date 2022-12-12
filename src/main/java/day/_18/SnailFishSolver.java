package day._18;

import util.common.Solver;

import java.util.List;
import java.util.stream.Collectors;

public class SnailFishSolver extends Solver<Long> {

    private List<SnailFishNumber> numbers;
    private SnailFishNumber result;

    public SnailFishSolver(String filename) {
        super(filename);
    }

    private List<SnailFishNumber> initSnailFishNumbers() {
        return puzzle.stream().map(SnailFishNumber::parseANumber).collect(Collectors.toList());
    }

    @Override
    protected Long solvePartOne() {
        numbers = initSnailFishNumbers();
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
        numbers = initSnailFishNumbers();
        long maxMagnitude = Long.MIN_VALUE;
        for (int i = 0; i < numbers.size(); i++) {
            for (int j = 0; j < numbers.size(); j++) {
                if (i != j) {
                    SnailFishNumber firstCopy = numbers.get(i).copy();
                    SnailFishNumber secondCopy = numbers.get(j).copy();
                    SnailFishNumber add = firstCopy.add(secondCopy);
                    add.reduce();
                    maxMagnitude = Math.max(maxMagnitude, add.magnitude());
                }
            }
        }
        return maxMagnitude;
    }

    public SnailFishNumber getResult() {
        return result;
    }

}
