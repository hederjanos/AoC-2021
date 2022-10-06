package graphsystem;

import graphsystem.graph.Edge;
import graphsystem.graph.SimpleGridGraph;
import graphsystem.graph.WeightedIntegerGraph;
import graphsystem.path.Path;
import util.PuzzleReader;

import java.util.*;

public class Application {

    public static void main(String[] args) {
        //simple (No.: 11) 4 full
        //level1 (No.: 25) 6 break
        //level2 (No.: 43)
        //level3 (No.: 100)
        PuzzleReader puzzleReader = new PuzzleReader();
        puzzleReader.readPuzzle("simple.in");
        SimpleGridGraph simpleGridGraph = new SimpleGridGraph(puzzleReader.getPuzzleLines());
        WeightedIntegerGraph weightedIntegerGraph = new WeightedIntegerGraph();
        weightedIntegerGraph.transFormSimpleGridGraphByCriticalNodes(simpleGridGraph, 4);

        //trial
        System.out.println("-------------------------------------------------------------");
        System.out.println("WeightedIntegerGraph representation of input: ");
        System.out.println("-------------------------------------------------------------");
        System.out.println(weightedIntegerGraph);
        System.out.println("-------------------------------------------------------------");
        System.out.println("Solution:");
        System.out.println("-------------------------------------------------------------");
        Optional<Path<Integer, Integer>> min = calculate(simpleGridGraph, weightedIntegerGraph);
        System.out.println("Start node: " + simpleGridGraph.encodeNode(simpleGridGraph.getStartNode()));
        System.out.println("Cost: " + min.get().getWeight());
        System.out.println("Path: ");
        min.get().stream().map(simpleGridGraph::decodeNode).forEach(System.out::println);
    }

    private static Optional<Path<Integer, Integer>> calculate(SimpleGridGraph simpleGridGraph, WeightedIntegerGraph weightedIntegerGraph) {
        List<Path<Integer, Integer>> solutions = new ArrayList<>();
        Deque<Path<Integer, Integer>> paths = new ArrayDeque<>();
        Deque<List<Integer>> pathHelpers = new ArrayDeque<>();
        Path<Integer, Integer> startPath = new Path<>();
        List<Integer> startHelper = new ArrayList<>();
        startHelper.add(weightedIntegerGraph.getStartNode());
        startPath.addNode(weightedIntegerGraph.getStartNode(), 0);
        paths.push(startPath);
        pathHelpers.push(startHelper);
        while (!paths.isEmpty()) {
            Path<Integer, Integer> path = paths.pop();
            List<Integer> pathHelper = pathHelpers.pop();
            if (path.size() == simpleGridGraph.getCriticalNodes().size()) {
                Path<Integer, Integer> newPath = path.copy();
                solutions.add(newPath);
            }
            Integer lastNode = path.get(path.size() - 1);
            int numberOfUnVisitedNeighbours = 0;
            Iterable<Edge<Integer, Integer>> edges = weightedIntegerGraph.getEdges(lastNode);
            List<Edge<Integer, Integer>> targets = new ArrayList<>();
            for (Edge<Integer, Integer> edge : edges) {
                if (edge.getSource().equals(lastNode)) {
                    targets.add(new Edge<>(edge.getSource(), edge.getTarget(), edge.getWeight()));
                } else {
                    targets.add(new Edge<>(edge.getTarget(), edge.getSource(), edge.getWeight()));
                }
            }
            for (Edge<Integer, Integer> edge : targets) {
                if (!path.contains(edge.getTarget()) && !pathHelper.contains(edge.getTarget())) {
                    numberOfUnVisitedNeighbours++;
                    Path<Integer, Integer> newPath = path.copy();
                    newPath.addNode(edge.getTarget(), edge.getWeight());
                    paths.push(newPath);
                    List<Integer> help = new ArrayList<>(pathHelper);
                    help.add(edge.getTarget());
                    pathHelpers.push(help);
                }
            }
            if (numberOfUnVisitedNeighbours == 0 && path.size() != simpleGridGraph.getCriticalNodes().size()) {
                Integer node;
                List<Integer> neighbours;
                do {
                    node = path.removeNode(path.size() - 1);
                    neighbours = ((List<Integer>) weightedIntegerGraph.getNeighbours(node));
                } while (!path.isEmpty() && neighbours.stream().anyMatch(integer -> !path.contains(integer)));
                if (!path.isEmpty()) {
                    paths.push(path);
                    pathHelpers.push(pathHelper);
                }
            }
        }
        return solutions.stream().min((o1, o2) -> {
            if (o1.getWeight() < o2.getWeight()) {
                return -1;
            } else if (o1.getWeight() > o2.getWeight()) {
                return 1;
            }
            return 0;
        });
    }

}