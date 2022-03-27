package daysix;

import util.Solver;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class LanternFishSolver extends Solver<Long> {

    private final List<Integer> lanternFishAges;

    protected LanternFishSolver(String filename) {
        super(filename);
        this.lanternFishAges = parseAges();
    }

    private List<Integer> parseAges() {
        return Arrays.stream(puzzle.get(0).split(","))
                .map(Integer::parseInt).collect(Collectors.toList());
    }

    @Override
    protected Long solvePartOne() {
        long[] counterArray = new long[9];
        for (Integer age : lanternFishAges) {
            counterArray[age]++;
        }
        for (int i = 0; i < 80; i++) {
            long firstElement = counterArray[0];
            counterArray = shiftArray(counterArray);
            counterArray[6] += firstElement;
        }
        return Arrays.stream(counterArray).sum();
    }

    private long[] shiftArray(long[] counterArray) {
        long[] shiftedArray = new long[counterArray.length];
        long firstElement = counterArray[0];
        System.arraycopy(counterArray, 1, shiftedArray, 0, counterArray.length - 1);
        shiftedArray[shiftedArray.length - 1] = firstElement;
        return shiftedArray;
    }

    @Override
    protected Long solvePartTwo() {
        return null;
    }

}
