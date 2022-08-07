package graph.path;

import graph.Graph;

import java.util.ArrayDeque;
import java.util.Queue;

public class BreadthSearchPathFinder<N> implements PathFinder<N> {

    protected boolean[] isVisited;
    protected int[] numberOfMoves;
    protected int[] pathMemory;
    protected Graph<N> graph;

    @Override
    public void findAllPath(Graph<N> graph, N start) {
        if (graph.getStartNode() == null) {
            graph.setStartNode(start);
        }

        this.graph = graph;
        isVisited =new boolean[graph.getNumberOfNodes()];
        numberOfMoves = new int[graph.getNumberOfNodes()];
        pathMemory = new int[graph.getNumberOfNodes()];

        Queue<Integer> nodes = new ArrayDeque<>();
        nodes.offer(graph.encodeNode(graph.getStartNode()));
        while (!nodes.isEmpty()) {
            int lastNodeIndex = nodes.poll();
            N lastNode = graph.decodeNode(lastNodeIndex);
            for (N neighbour : graph.getNeighbours(lastNode)) {
                int neighbourIndex = graph.encodeNode(neighbour);
                if (!isVisited[neighbourIndex]) {
                    pathMemory[neighbourIndex] = lastNodeIndex;
                    numberOfMoves[neighbourIndex] = numberOfMoves[lastNodeIndex] + 1;
                    isVisited[neighbourIndex] = true;
                    nodes.offer(neighbourIndex);
                }
            }
        }
    }

    public Path<N> pathTo(N node) {
        Path<N> route = new Path<>();
        int x;
        for (x = graph.encodeNode(node); numberOfMoves[x] != 0; x = pathMemory[x]) {
            route.offer(graph.decodeNode(x));
        }
        route.offer(graph.decodeNode(x));
        return route;
    }

}
