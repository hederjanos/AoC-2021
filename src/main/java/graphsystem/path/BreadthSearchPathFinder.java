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

    public Iterable<N> getClosestCriticalNodesByCost(N source, int numberOfNeighbours, boolean isAscending) {
        if (pathCalculatedFrom == null) {
            throw new IllegalArgumentException();
        }
        if (!pathCalculatedFrom.equals(source)) {
            findAllPathsFromNode(source);
        }
        Comparator<PriorityNode<N, Integer>> nodeComparator = null;
        if (!isAscending) {
            nodeComparator = (node1, node2) -> {
                if (node1.getPriority() < node2.getPriority()) {
                    return 1;
                } else if (node1.getPriority() > node2.getPriority()) {
                    return -1;
                }
                return 0;
            };
        }
        Queue<PriorityNode<N, Integer>> nodes = collectNodes(source, numberOfNeighbours, nodeComparator);
        List<PriorityNode<N, Integer>> sortedNodes = new ArrayList<>();
        while (!nodes.isEmpty()) {
            sortedNodes.add(nodes.poll());
        }
        if (!isAscending) {
            Collections.reverse(sortedNodes);
        }
        return sortedNodes.stream().map(PriorityNode::getNode).collect(Collectors.toList());
    }

    private Queue<PriorityNode<N, Integer>> collectNodes(N source, int numberOfNeighbours, Comparator<PriorityNode<N, Integer>> nodeComparator) {
        Queue<PriorityNode<N, Integer>> nodes = new PriorityQueue<>(nodeComparator);
        for (N node : graph.getCriticalNodes()) {
            if (!node.equals(source) && nodeIsReachable(node)) {
                Integer moveCostFromSource = numberOfMoves[graph.encodeNode(node)];
                if (nodes.size() >= numberOfNeighbours) {
                    PriorityNode<N, Integer> currentNode = nodes.poll();
                    if (currentNode.getPriority() > moveCostFromSource) {
                        nodes.offer(new PriorityNode<>(node, moveCostFromSource));
                    } else {
                        nodes.offer(currentNode);
                    }
                } else {
                    nodes.offer((new PriorityNode<>(node, moveCostFromSource)));
                }
            }
        }
        return nodes;
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