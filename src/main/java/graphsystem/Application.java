package graphsystem;

import graphsystem.graph.Graph;
import graphsystem.graph.GraphWithEdges;
import graphsystem.graph.SimpleGridGraph;
import graphsystem.graph.WeightedIntegerGraph;
import graphsystem.grid.GridCell;
import graphsystem.path.Path;
import util.PuzzleReader;

import java.util.List;

public class Application {

    public static void main(String[] args) {

        PuzzleReader puzzleReader = new PuzzleReader();
        puzzleReader.readPuzzle("simple.in");
        SimpleGridGraph graph = new SimpleGridGraph(puzzleReader.getPuzzleLines());
        System.out.println(graph);
        System.out.println("-------------------------------------------------------------");
        GraphWithEdges<Integer, Double> weightedIntegerGraph = new WeightedIntegerGraph(graph);
        System.out.println(weightedIntegerGraph);
        System.out.println("-------------------------------------------------------------");
        List<GridCell> nodes = graph.getMustBeVisitedCells();
        List<Path<GridCell>> permutations = Path.findPermutations(nodes);
        permutations.forEach(System.out::println);

    }

}
