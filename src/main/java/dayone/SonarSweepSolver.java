package dayone;

import util.common.Solver;

import java.util.stream.IntStream;

public class SonarSweepSolver extends Solver<Integer> {

    public SonarSweepSolver(String filename) {
        super(filename);
    }

    @Override
    public Integer solvePartOne() {
        return solve(1);
    }

    @Override
    protected Integer solvePartTwo() {
        return solve(3);
    }

    private Integer solve(int range) {
        return (int) IntStream.iterate(puzzle.size() - 1, i -> i > range - 1, i -> i - 1)
                .filter(i -> getSumByRange(i - 1, range) < getSumByRange(i, range))
                .count();
    }

    private int getSumByRange(int head, int range) {
        return puzzle.subList(head - range + 1, head + 1).stream().mapToInt(Integer::valueOf).sum();
    }

}
