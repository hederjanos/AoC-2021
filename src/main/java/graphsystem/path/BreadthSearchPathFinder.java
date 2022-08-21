package graphsystem.path;

import graphsystem.graph.Graph;

import java.util.ArrayDeque;
import java.util.Collections;
import java.util.Queue;

public class BreadthSearchPathFinder<N> implements PathFinder<N> {

    protected boolean[] isVisited;
    protected int[] numberOfMoves;
    protected int[] pathMemory;
    protected Graph<N> graph;

    public BreadthSearchPathFinder(Graph<N> graph) {
        this.graph = graph;
        isVisited = new boolean[graph.getNumberOfNodes()];
        numberOfMoves = new int[graph.getNumberOfNodes()];
        pathMemory = new int[graph.getNumberOfNodes()];
    }

    @Override
    public void findAllPathsFromDefaultStartNode() {
        if (graph.getStartNode().isEmpty()) {
            throw new IllegalArgumentException();
        } else {
            findAllPaths(graph.getStartNode().get());
        }
    }

    @Override
    public void findAllPathsFromNode(N start) {
        if (graph.getNode(start).isEmpty()) {
            throw new IllegalArgumentException();
        } else {
            findAllPaths(start);
        }
    }

    private void findAllPaths(N start) {
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

    public Path<N> pathTo(N node) {
        if (graph == null || graph.getStartNode().isEmpty()) {
            throw new IllegalArgumentException();
        }
        Path<N> route = new Path<>();
        int x = graph.encodeNode(node);
        while (numberOfMoves[x] != 0) {
            route.offer(graph.decodeNode(x));
            x = pathMemory[x];
        }
        route.offer(graph.decodeNode(x));
        Collections.reverse(route);
        return route;
    }

}
