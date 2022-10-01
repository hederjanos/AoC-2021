package graphsystem;

import graphsystem.graph.SimpleGridGraph;
import graphsystem.graph.WeightedIntegerGraph;
import graphsystem.grid.GridCell;
import graphsystem.path.DijkstraPathFinder;
import graphsystem.path.Path;
import util.PuzzleReader;

public class Application {

    public static void main(String[] args) {

        PuzzleReader puzzleReader = new PuzzleReader();
        puzzleReader.readPuzzle("simple.in");
        SimpleGridGraph simpleGridGraph = new SimpleGridGraph(puzzleReader.getPuzzleLines());
        System.out.println(simpleGridGraph);
        System.out.println("-------------------------------------------------------------");
        WeightedIntegerGraph weightedIntegerGraph = new WeightedIntegerGraph();
        weightedIntegerGraph.transFormSimpleGridGraphByCriticalNodes(simpleGridGraph, 4);
        System.out.println(weightedIntegerGraph);
        System.out.println("-------------------------------------------------------------");
        DijkstraPathFinder<Integer, Double> pathFinder = new DijkstraPathFinder<>(weightedIntegerGraph);
        pathFinder.findAllPathsFromDefaultStartNode();
        for (GridCell node : simpleGridGraph.getCriticalNodes()) {
            Path<Integer> integers = pathFinder.pathTo(simpleGridGraph.encodeNode(node));
            System.out.println(simpleGridGraph.getStartNode());
            integers.stream().map(simpleGridGraph::decodeNode).forEach(System.out::println);
            System.out.println("-------------------------------------------------------------");
        }
    }

}
