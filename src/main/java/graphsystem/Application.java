package graphsystem;

import graphsystem.graph.SimpleGridGraph;
import util.PuzzleReader;

/**
 * simple (No.: 11)
 * level1 (No.: 25)
 * level2 (No.: 43)
 * level3 (No.: 102, not connected!!!)
 */
public class Application {

    public static void main(String[] args) {
        PuzzleReader puzzleReader = new PuzzleReader();
        puzzleReader.readPuzzle("simple.in");
        SimpleGridGraph simpleGridGraph = new SimpleGridGraph(puzzleReader.getPuzzleLines());
    }


}