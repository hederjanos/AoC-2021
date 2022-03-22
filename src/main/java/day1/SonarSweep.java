package day1;

import util.PuzzleReader;

public class SonarSweep {

    private final PuzzleReader puzzleReader = new PuzzleReader();

    public SonarSweep(String filename) {
        puzzleReader.readPuzzle(filename);
    }

    public void solve() {
        puzzleReader.printPuzzle();
    }
}
