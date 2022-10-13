package graphsystem;

import graphsystem.graph.Edge;
import graphsystem.graph.IntegerGraphWithEdges;
import graphsystem.graph.SimpleGridGraph;
import graphsystem.grid.GridCell;
import graphsystem.path.Path;
import graphsystem.traverse.NearestNeighbourTraverser;
import util.PuzzleReader;

import java.util.*;

public class Application {


    public static void main(String[] args) {
        //simple (No.: 11, OK)
        //level1 (No.: 25)
        //level2 (No.: 43)
        //level3 (No.: 102, not connected!!!)
        PuzzleReader puzzleReader = new PuzzleReader();
        puzzleReader.readPuzzle("simple.in");
        SimpleGridGraph simpleGridGraph = new SimpleGridGraph(puzzleReader.getPuzzleLines());

        NearestNeighbourTraverser<GridCell> traverser = new NearestNeighbourTraverser<>(simpleGridGraph);
        System.out.println(traverser.getSolution());

        IntegerGraphWithEdges weightedIntegerGraph = new IntegerGraphWithEdges();
        weightedIntegerGraph.transFormSimpleGridGraphByCriticalNodes(simpleGridGraph, 4, true);
        System.out.println(weightedIntegerGraph);

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

    private static Optional<Path<Integer, Integer>> calculate(SimpleGridGraph simpleGridGraph, IntegerGraphWithEdges
            weightedIntegerGraph) {
        Path<Integer, Integer> solution = new Path<>();
        Deque<Path<Integer, Integer>> paths = new ArrayDeque<>();
        Deque<int[]> visitedNodeRegisters = new ArrayDeque<>();
        Path<Integer, Integer> startPath = new Path<>();
        int[] startBook = new int[simpleGridGraph.getNumberOfCriticalNodes()];
        startBook[weightedIntegerGraph.encodeNode(weightedIntegerGraph.getStartNode())] = 1;
        startPath.addNode(weightedIntegerGraph.getStartNode(), 0);
        paths.push(startPath);
        visitedNodeRegisters.push(startBook);
        while (!paths.isEmpty()) {
            Path<Integer, Integer> path = paths.pop();
            int[] nodeRegister = visitedNodeRegisters.pop();
            System.out.println("S-------------------------------------------------------------");
            System.out.println("path: " + path.size() + ", " + path);
            System.out.println("remaining paths: " + paths.size());
            System.out.println("current solution: " + solution.size() + ", " + solution);
            System.out.println("E-------------------------------------------------------------");
            if (path.getWeight() > solution.getWeight()) {
                continue;
            }
            if (Arrays.stream(nodeRegister).filter(g -> g > 0).count() == simpleGridGraph.getNumberOfCriticalNodes()) {
                Path<Integer, Integer> newPath = path.copy();
                if (solution.getWeight() > newPath.getWeight()) {
                    solution = newPath;
                    continue;
                }
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
                if (!path.contains(edge.getTarget()) && path.getWeight() + edge.getWeight() < solution.getWeight() && !edge.getTarget().equals(lastNode)) {
                    numberOfUnVisitedNeighbours++;
                    Path<Integer, Integer> newPath = path.copy();
                    newPath.addNode(edge.getTarget(), edge.getWeight());
                    paths.push(newPath);
                    int[] newNodeRegister = Arrays.copyOf(nodeRegister, nodeRegister.length);
                    newNodeRegister[weightedIntegerGraph.encodeNode(edge.getTarget())] += 1;
                    visitedNodeRegisters.push(newNodeRegister);
                }
            }
            boolean pathIsUpdatable = true;
            if (numberOfUnVisitedNeighbours == 0) {
                Path<Integer, Integer> modifiedPath = path.copy();
                path.removeNode(path.size() - 1);
                List<Integer> neighbours;
                do {
                    Integer weight = path.getWeightIncrement(path.size() - 1);
                    Integer removeNode = path.removeNode(path.size() - 1);
                    if (removeNode.equals(weightedIntegerGraph.getStartNode())) {
                        pathIsUpdatable = false;
                        break;
                    }
                    modifiedPath.addNode(removeNode, weight);
                    nodeRegister[weightedIntegerGraph.encodeNode(removeNode)] += 1;
                    neighbours = ((List<Integer>) weightedIntegerGraph.getNeighbours(removeNode));

                } while (!path.isEmpty() && neighbours.stream().filter(path::contains).count() == neighbours.size());
                if (pathIsUpdatable) {
                    paths.push(modifiedPath);
                    visitedNodeRegisters.push(nodeRegister);
                }
            }
        }
        return Optional.of(solution);
    }

}