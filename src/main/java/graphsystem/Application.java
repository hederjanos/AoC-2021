package graphsystem;

import graphsystem.graph.Graph;
import graphsystem.graph.SimpleGridGraph;
import graphsystem.graph.WeightedIntegerGraph;
import graphsystem.grid.GridCell;
import util.PuzzleReader;

public class Application {

    public static void main(String[] args) {

        PuzzleReader puzzleReader = new PuzzleReader();
        puzzleReader.readPuzzle("level3.in");
        Graph<GridCell> graph = new SimpleGridGraph(puzzleReader.getPuzzleLines());

        WeightedIntegerGraph weightedIntegerGraph = new WeightedIntegerGraph(graph);
        System.out.println(weightedIntegerGraph);

    }

}
