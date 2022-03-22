package util;

import java.util.List;

public abstract class Solver<V> {

    protected List<String> puzzle;

    public Solver(String filename) {
        PuzzleReader puzzleReader = new PuzzleReader();
        puzzleReader.readPuzzle(filename);
        this.puzzle = puzzleReader.getPuzzleLines();
    }

    protected abstract V solve();

    protected abstract void printResult();
}
