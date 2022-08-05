package graph.path;

import graph.Graph;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;

public class BreadthSearchPathFinder<V> implements PathFinder<V> {

    protected boolean[] isVisited;
    protected int[] numberOfMoves;
    protected int[] pathMemory;
    protected Graph<V> graph;

    @Override
    public void findAllPath(Graph<V> graph, V start) {
        if (graph.getStart() == null) {
            graph.setStart(start);
        }

        this.graph = graph;
        isVisited =new boolean[graph.getNumberOfVertices()];
        numberOfMoves = new int[graph.getNumberOfVertices()];
        pathMemory = new int[graph.getNumberOfVertices()];

        Queue<Integer> vertices = new ArrayDeque<>();
        vertices.offer(graph.encodeVertex(graph.getStart()));
        while (!vertices.isEmpty()) {
            int lastVertexIndex = vertices.poll();
            V lastVertex = graph.decodeVertex(lastVertexIndex);
            for (V neighbour : graph.getNeighbours(lastVertex)) {
                int neighbourIndex = graph.encodeVertex(neighbour);
                if (!isVisited[neighbourIndex]) {
                    pathMemory[neighbourIndex] = lastVertexIndex;
                    numberOfMoves[neighbourIndex] = numberOfMoves[lastVertexIndex] + 1;
                    isVisited[neighbourIndex] = true;
                    vertices.offer(neighbourIndex);
                }
            }
        }
    }

    public Path<V> pathTo(V vertex) {
        Path<V> route = new Path<>();
        int x;
        for (x = graph.encodeVertex(vertex); numberOfMoves[x] != 0; x = pathMemory[x]) {
            route.offer(graph.decodeVertex(x));
        }
        route.offer(graph.decodeVertex(x));
        return route;
    }

}
