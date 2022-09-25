package graphsystem;

import graphsystem.graph.Graph;
import graphsystem.graph.SimpleGridGraph;
import graphsystem.graph.WeightedIntegerGraph;
import graphsystem.grid.GridCell;
import graphsystem.path.DijkstraPathFinder;
import util.PuzzleReader;

public class Application {

    public static void main(String[] args) {

        PuzzleReader puzzleReader = new PuzzleReader();
        puzzleReader.readPuzzle("simple.in");
        Graph<GridCell> graph = new SimpleGridGraph(puzzleReader.getPuzzleLines());
        System.out.println(graph);
        System.out.println("---------------------------------------------------------------------------------------------------------------");
        WeightedIntegerGraph weightedIntegerGraph = new WeightedIntegerGraph(graph);
        System.out.println(weightedIntegerGraph);
        DijkstraPathFinder<Integer, Double> pathFinder = new DijkstraPathFinder<>(weightedIntegerGraph);
        pathFinder.findAllPathsFromDefaultStartNode();

        for (Integer node : weightedIntegerGraph.getAllNodes()) {
            System.out.print(weightedIntegerGraph.getStartNode());
            System.out.print("->");
            System.out.print(node);
            System.out.print(": ");
            System.out.println(pathFinder.pathTo(node));
        }

    }

}
