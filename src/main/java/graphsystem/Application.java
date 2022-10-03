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
        Optional<Path<Integer>> min = calculate(simpleGridGraph, weightedIntegerGraph);

        System.out.println("-------------------------------------------------------------");
        System.out.println("solution");
        System.out.println("-------------------------------------------------------------");

        //System.out.println(simpleGridGraph);
        System.out.println("-------------------------------------------------------------");
        System.out.println(weightedIntegerGraph);
        System.out.println("-------------------------------------------------------------");
        System.out.println("start: " + simpleGridGraph.encodeNode(simpleGridGraph.getStartNode()));
        System.out.println("cost: " + min.get().getWeight());
        min.get().stream().map(simpleGridGraph::decodeNode).forEach(System.out::println);

    }

    private static Optional<Path<Integer>> calculate(SimpleGridGraph simpleGridGraph, WeightedIntegerGraph weightedIntegerGraph) {
        List<Path<Integer>> solutions = new ArrayList<>();
        Deque<Path<Integer>> paths = new ArrayDeque<>();
        Deque<List<Integer>> pathHelpers = new ArrayDeque<>();
        Path<Integer> startPath = new Path<>();
        List<Integer> startHelper = new ArrayList<>();
        startHelper.add(weightedIntegerGraph.getStartNode());
        startPath.addNode(weightedIntegerGraph.getStartNode(), 0d);
        paths.push(startPath);
        pathHelpers.push(startHelper);
        while (!paths.isEmpty()) {
            Path<Integer> path = paths.pop();
            List<Integer> pathHelper = pathHelpers.pop();
            System.out.print("current path length: " + path.size());
            System.out.println(", remaining paths: " + paths.size());

            if (path.size() == simpleGridGraph.getCriticalNodes().size()) {
                Path<Integer> newPath = path.copy();
                solutions.add(newPath);
            }
            Integer last = path.get(path.size() - 1);
            int counter = 0;

            Iterable<Edge<Integer, Double>> edges = weightedIntegerGraph.getEdges(last);
            List<Edge<Integer, Double>> targets = new ArrayList<>();
            for (Edge<Integer, Double> edge : edges) {
                if (edge.getSource().equals(last)) {
                    targets.add(new Edge<>(edge.getSource(), edge.getTarget(), edge.getWeight()));
                } else {
                    targets.add(new Edge<>(edge.getTarget(), edge.getSource(), edge.getWeight()));
                }
            }

            for (Edge<Integer, Double> edge : targets) {
                if (!path.contains(edge.getTarget()) && !pathHelper.contains(edge.getTarget())) {
                    counter++;
                    Path<Integer> newPath = path.copy();
                    newPath.addNode(edge.getTarget(), edge.getWeight());
                    paths.push(newPath);
                    List<Integer> help = new ArrayList<>(pathHelper);
                    help.add(edge.getTarget());
                    pathHelpers.push(help);
                }
            }
            if (counter == 0) {
                List<Integer> neighbours;
                do {
                    Integer node = path.removeNode(path.size() - 1);
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
