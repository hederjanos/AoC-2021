package day1;

import util.PuzzleReader;
import util.Solver;

import java.util.List;
import java.util.stream.IntStream;

public class SonarSweepSolver<V> implements Solver<Integer> {

    List<String> puzzle;

    public SonarSweepSolver(String filename) {
        PuzzleReader puzzleReader = new PuzzleReader();
        puzzleReader.readPuzzle(filename);
        this.puzzle = puzzleReader.getPuzzleLines();
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
