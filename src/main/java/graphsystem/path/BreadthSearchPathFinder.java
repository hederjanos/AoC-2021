package graphsystem.path;

import graphsystem.graph.Graph;
import graphsystem.grid.GridCell;

import java.util.*;
import java.util.stream.Collectors;

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

    public Iterable<N> getClosestCriticalNodesByCost(N source, int numberOfNeighbours) {
        if (pathCalculatedFrom == null) {
            throw new IllegalArgumentException();
        }
        if (!pathCalculatedFrom.equals(source)) {
            findAllPathsFromNode(source);
        }
        Queue<NodeWithPriority<N>> nodes = new PriorityQueue<>((o1, o2) -> {
            if (o1.getPriority() < o2.getPriority()) {
                return 1;
            } else if (o1.getPriority() > o2.getPriority()) {
                return -1;
            }
            return 0;
        });
        for (N node : graph.getCriticalNodes()) {
            if (!node.equals(source) && nodeIsReachable(node)) {
                int moveCostFromSource = numberOfMoves[graph.encodeNode(node)];
                System.out.println("node " + moveCostFromSource);
                if (nodes.size() >= numberOfNeighbours) {
                    System.out.println("nodes " + nodes.stream().map(NodeWithPriority::getPriority).collect(Collectors.toUnmodifiableList()));
                    NodeWithPriority<N> lowestPriorityNode = nodes.poll();
                    System.out.println("polled " + lowestPriorityNode.getPriority());
                    if (lowestPriorityNode.getPriority() > moveCostFromSource) {
                        nodes.offer(new NodeWithPriority<>(node, moveCostFromSource));
                   /* } else if (lowestPriorityNode.getPriority() == moveCostFromSource) {
                        GridCell source2 = (GridCell) source;
                        GridCell node1 = (GridCell) node;
                        GridCell node2 = (GridCell) lowestPriorityNode.getNode();
                        if (source2.getPosition().calculateSquareDistance(node1.getPosition()) < source2.getPosition().calculateSquareDistance(node2.getPosition())) {
                            nodes.offer(new NodeWithPriority<>((N) node1, moveCostFromSource));
                        } else {
                            nodes.offer(new NodeWithPriority<>((N) node2, moveCostFromSource));
                        }*/
                    } else {
                        nodes.offer(lowestPriorityNode);
                    }
                } else {
                    nodes.offer((new NodeWithPriority<>(node, moveCostFromSource)));
                }
            }
        }
        return nodes.stream().map(NodeWithPriority::getNode).collect(Collectors.toList());
    }

}
