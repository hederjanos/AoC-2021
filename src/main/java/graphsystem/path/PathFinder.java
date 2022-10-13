package graphsystem.path;

import graphsystem.graph.Graph;

import java.util.Collections;

public abstract class PathFinder<N, W extends Number> {

    protected int[] numberOfMoves;
    protected int[] pathMemory;
    protected Graph<N> graph;
    protected N pathCalculatedFrom;

    protected void initializeAuxiliaryArrays() {
        numberOfMoves = new int[graph.getNumberOfNodes()];
        pathMemory = new int[graph.getNumberOfNodes()];
    }

    public Path<N, W> pathTo(N target) {
        if (pathCalculatedFrom == null) {
            throw new IllegalArgumentException();
        }
        Path<N, W> path = new Path<>();
        int indexOfNode = graph.encodeNode(target);
        while (pathMemory[indexOfNode] != 0) {
            path.add(graph.decodeNode(indexOfNode));
            indexOfNode = pathMemory[indexOfNode];
        }
        path.add(graph.decodeNode(indexOfNode));
        Collections.reverse(path);
        return path;
    }

    public abstract void findAllPathsFromDefaultStartNode();

    public abstract void findAllPathsFromNode(N start);

    public abstract boolean nodeIsReachable(N target);

    public abstract int getNumberOfMovesTo(N target);

    public abstract Iterable<N> getClosestCriticalNodesByCost(N source, int numberOfNeighbours, boolean isAscending);

    public abstract N getFurthestNode(N source);

}