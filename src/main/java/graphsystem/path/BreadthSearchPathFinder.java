package graphsystem.path;

import graphsystem.graph.Graph;

import java.util.ArrayDeque;
import java.util.Queue;

public class BreadthSearchPathFinder<N> extends PathFinder<N> {

    private boolean[] isVisited;

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

    @Override
    protected void initializeAuxiliaryArrays() {
        super.initializeAuxiliaryArrays();
        isVisited = new boolean[graph.getNumberOfNodes()];
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
