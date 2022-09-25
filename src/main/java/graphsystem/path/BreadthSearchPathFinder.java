package graphsystem.path;

import graphsystem.graph.Graph;

import java.util.ArrayDeque;
import java.util.Collections;
import java.util.Queue;

public class BreadthSearchPathFinder<N> implements PathFinder<N> {

    private boolean[] isVisited;
    private int[] numberOfMoves;
    private int[] pathMemory;
    private final Graph<N> graph;
    private N pathCalculatedFrom;

    public BreadthSearchPathFinder(Graph<N> graph) {
        if (graph == null) {
            throw new IllegalArgumentException();
        }
        this.graph = graph;
    }

    @Override
    public void findAllPathsFromDefaultStartNode() {
        findAllPaths(graph.getStartNode());
    }

    @Override
    public void findAllPathsFromNode(N start) {
        if (graph.getNode(start).isEmpty()) {
            throw new IllegalArgumentException();
        }
        initializeAuxiliaryArrays();
        findAllPaths(start);
    }

    private void initializeAuxiliaryArrays() {
        isVisited = new boolean[graph.getNumberOfNodes()];
        numberOfMoves = new int[graph.getNumberOfNodes()];
        pathMemory = new int[graph.getNumberOfNodes()];
    }

    private void findAllPaths(N start) {
        pathCalculatedFrom = start;
        Queue<Integer> nodes = new ArrayDeque<>();
        int startNodeIndex = graph.encodeNode(start);
        nodes.offer(startNodeIndex);
        numberOfMoves[startNodeIndex] = 0;
        isVisited[startNodeIndex] = true;
        while (!nodes.isEmpty()) {
            int lastNodeIndex = nodes.poll();
            N lastNode = graph.decodeNode(lastNodeIndex);
            for (N neighbour : graph.getNeighbours(lastNode)) {
                int neighbourIndex = graph.encodeNode(neighbour);
                if (graph.nodeIsPassable(neighbour) && !isVisited[neighbourIndex]) {
                    pathMemory[neighbourIndex] = lastNodeIndex;
                    numberOfMoves[neighbourIndex] = numberOfMoves[lastNodeIndex] + 1;
                    isVisited[neighbourIndex] = true;
                    nodes.offer(neighbourIndex);
                }
            }
        }
    }

    public Path<N> pathTo(N target) {
        if (pathCalculatedFrom == null) {
            throw new IllegalArgumentException();
        }
        Path<N> route = new Path<>();
        int indexOfNode = graph.encodeNode(target);
        while (numberOfMoves[indexOfNode] != 0) {
            route.offer(graph.decodeNode(indexOfNode));
            indexOfNode = pathMemory[indexOfNode];
        }
        route.offer(graph.decodeNode(indexOfNode));
        Collections.reverse(route);
        return route;
    }

    @Override
    public boolean nodeIsReachable(N target) {
        if (pathCalculatedFrom == null || graph.getNode(target).isEmpty()) {
            throw new IllegalArgumentException();
        }
        return isVisited[graph.encodeNode(target)];
    }

    @Override
    public int getNumberOfMovesTo(N target) {
        if (pathCalculatedFrom == null || graph.getNode(target).isEmpty()) {
            throw new IllegalArgumentException();
        }
        return numberOfMoves[graph.encodeNode(target)];
    }

}
