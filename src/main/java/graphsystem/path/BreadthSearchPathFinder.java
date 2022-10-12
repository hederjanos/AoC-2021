package graphsystem.path;

import graphsystem.graph.Graph;

import java.util.*;
import java.util.stream.Collectors;

public class BreadthSearchPathFinder<N, W extends Number, P extends Comparable<P>> extends PathFinder<N, W> {

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

    public Iterable<N> getClosestCriticalNodesByCost(N source, int numberOfNeighbours) {
        if (pathCalculatedFrom == null) {
            throw new IllegalArgumentException();
        }
        if (!pathCalculatedFrom.equals(source)) {
            findAllPathsFromNode(source);
        }
        Queue<PriorityNode<N, Integer>> nodes = new PriorityQueue<>();
        for (N node : graph.getCriticalNodes()) {
            if (!node.equals(source) && nodeIsReachable(node)) {
                Integer moveCostFromSource = numberOfMoves[graph.encodeNode(node)];
                if (nodes.size() >= numberOfNeighbours) {
                    PriorityNode<N, Integer> maxPriorityNode = nodes.poll();
                    if (maxPriorityNode.getPriority() > moveCostFromSource) {
                        nodes.offer(new PriorityNode<>(node, moveCostFromSource));
                    } else {
                        nodes.offer(maxPriorityNode);
                    }
                } else {
                    nodes.offer((new PriorityNode<>(node, moveCostFromSource)));
                }
            }
        }
        List<PriorityNode<N, Integer>> k = new ArrayList<>();
        while (!nodes.isEmpty()) {
            k.add(nodes.poll());
        }
        return k.stream().map(PriorityNode::getNode).collect(Collectors.toList());
    }

    public N getFurthestNode(N source) {
        if (pathCalculatedFrom == null) {
            throw new IllegalArgumentException();
        }
        if (!pathCalculatedFrom.equals(source)) {
            findAllPathsFromNode(source);
        }
        N furthestNode = graph.getStartNode();
        for (N criticalNode : graph.getCriticalNodes()) {
            if (getNumberOfMovesTo(criticalNode) > getNumberOfMovesTo(furthestNode)) {
                furthestNode = criticalNode;
            }
        }
        return furthestNode;
    }

}