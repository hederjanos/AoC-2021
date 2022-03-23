package util;

import java.util.List;

public abstract class Solver<V> {

    protected List<String> puzzle;

    protected Solver(String filename) {
        PuzzleReader puzzleReader = new PuzzleReader();
        puzzleReader.readPuzzle(filename);
        this.puzzle = puzzleReader.getPuzzleLines();
    }

    protected abstract V solvePartOne();

    protected abstract V solvePartTwo();

    public void printResults() {
        System.out.println("----------------------------------------");
        System.out.println("Results of " + getClass().getSimpleName());
        System.out.println("----------------------------------------");
        System.out.println("First part: " + solvePartOne());
        System.out.println("Second part: " + solvePartTwo());
    }
}
