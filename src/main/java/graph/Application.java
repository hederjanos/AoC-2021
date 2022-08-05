package graph;

import graph.grid.GridCell;
import graph.path.BreadthSearchPathFinder;
import graph.path.PathFinder;
import util.PuzzleReader;


public class Application {

    public static void main(String[] args) {

        PuzzleReader puzzleReader = new PuzzleReader();
        puzzleReader.readPuzzle("simple-test.in");
        Graph<GridCell> graph = new SimpleGridGraph(puzzleReader.getPuzzleLines());

        PathFinder<GridCell> pathFinder = new BreadthSearchPathFinder<>();
        pathFinder.findAllPath(graph, new GridCell(0, 0));
        pathFinder.pathTo(new GridCell(10, 10)).forEach(System.out::println);
    }
}
