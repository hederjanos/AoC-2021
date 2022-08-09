package graphsystem;

import graphsystem.graph.Graph;
import graphsystem.graph.SimpleGridGraph;
import graphsystem.grid.GridCell;
import graphsystem.path.BreadthSearchPathFinder;
import graphsystem.path.PathFinder;
import util.PuzzleReader;


public class Application {

    public static void main(String[] args) {

        PuzzleReader puzzleReader = new PuzzleReader();
        puzzleReader.readPuzzle("simple-test.in");
        Graph<GridCell> graph = new SimpleGridGraph(puzzleReader.getPuzzleLines());

        PathFinder<GridCell> pathFinder = new BreadthSearchPathFinder<>();
        pathFinder.findAllPath(graph);
        pathFinder.pathTo(new GridCell(13, 9)).forEach(System.out::println);
    }
}
