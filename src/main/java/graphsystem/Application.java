package graphsystem;

import graphsystem.graph.Edge;
import graphsystem.graph.GraphWithEdges;
import graphsystem.graph.SimpleGridGraph;
import graphsystem.graph.WeightedIntegerGraph;
import graphsystem.grid.GridCell;
import graphsystem.path.DijkstraPathFinder;
import graphsystem.path.NodeWithPriority;
import graphsystem.path.Path;
import util.PuzzleReader;

import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;

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
        /*DijkstraPathFinder<Integer, Double> pathFinder = new DijkstraPathFinder<>(weightedIntegerGraph);
        pathFinder.findAllPathsFromDefaultStartNode();
        for (GridCell node : simpleGridGraph.getCriticalNodes()) {
            Path<Integer> integers = pathFinder.pathTo(simpleGridGraph.encodeNode(node));
            System.out.println(simpleGridGraph.getStartNode());
            integers.stream().map(simpleGridGraph::decodeNode).forEach(System.out::println);
            System.out.println("-------------------------------------------------------------");
        }*/

        //trial

        double[] weights = new double[weightedIntegerGraph.getNumberOfNodes()];
        Arrays.fill(weights, Double.MAX_VALUE);
        Queue<Path<Integer>> paths = new PriorityQueue<>();
        Path<Integer> startPath = new Path<>();
        startPath.setWeight(0d);
        Integer startNode = weightedIntegerGraph.encodeNode(weightedIntegerGraph.getStartNode());
        weights[startNode] = 0d;
        startPath.add(weightedIntegerGraph.getStartNode());
        paths.offer(startPath);
        while (!paths.isEmpty()) {
            Path<Integer> path = paths.poll();
            if (path.size() == simpleGridGraph.getCriticalNodes().size()) {
                path.stream().map(simpleGridGraph::decodeNode).forEach(System.out::println);
                break;
            }
            Integer last = path.get(path.size() - 1);
            Integer source = weightedIntegerGraph.encodeNode(last);
            for (Edge<Integer, Double> edge : weightedIntegerGraph.getEdges(last)) {
                Integer target = weightedIntegerGraph.encodeNode(edge.getTarget());
                if (weights[target] > weights[source] + edge.getWeight()) {
                    weights[target] = weights[source] + edge.getWeight();
                }
                if (!path.contains(edge.getTarget())) {
                    Path<Integer> newPath = new Path<>();
                    newPath.addAll(path);
                    newPath.add(edge.getTarget());
                    newPath.setWeight(path.getWeight() + edge.getWeight());
                    paths.offer(newPath);
                }
            }
        }
    }

}
