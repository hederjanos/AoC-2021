package day1;

import util.Solver;

import java.util.stream.IntStream;

public class SonarSweepSolver extends Solver<Integer> {

    public SonarSweepSolver(String filename) {
        super(filename);
    }

    @Override
    public Integer solve() {
        return (int) IntStream.iterate(puzzle.size() - 1, i -> i > 0, i -> i - 1)
                .filter(i -> Integer.parseInt(puzzle.get(i - 1)) < Integer.parseInt(puzzle.get(i)))
                .count();
    }

    @Override
    public void printResult() {
        System.out.println(solve());
    }
}
