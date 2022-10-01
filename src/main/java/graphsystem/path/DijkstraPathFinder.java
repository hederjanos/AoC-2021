package graphsystem.path;

import graphsystem.graph.Edge;
import graphsystem.graph.Graph;
import graphsystem.graph.GraphWithEdges;

import java.util.*;

public class DijkstraPathFinder<N, W extends Number> extends PathFinder<N> {

    private double[] weights;

    public DijkstraPathFinder(Graph<N> graph) {
        if (!(graph instanceof GraphWithEdges)) {
            throw new IllegalArgumentException();
        }
        this.graph = graph;
        initializeAuxiliaryArrays();
    }

    @Override
    protected void initializeAuxiliaryArrays() {
        super.initializeAuxiliaryArrays();
        weights = new double[graph.getNumberOfNodes()];
        Arrays.fill(weights, Double.MAX_VALUE);
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

    private void findAllPaths(N start) {
        pathCalculatedFrom = start;
        Integer startNode = graph.encodeNode(start);
        weights[startNode] = 0d;
        numberOfMoves[startNode] = 0;
        Queue<NodeWithPriority<Integer>> nodes = new PriorityQueue<>();
        NodeWithPriority<Integer> nodeWithPriority = new NodeWithPriority<>(startNode, weights[startNode]);
        nodes.offer(nodeWithPriority);
        while (!nodes.isEmpty()) {
            NodeWithPriority<Integer> minPriorityNode = nodes.poll();
            for (Edge<N, W> edge : ((GraphWithEdges<N, W>) graph).getEdges(graph.decodeNode(minPriorityNode.getNode()))) {
                Integer source = graph.encodeNode(edge.getSource());
                Integer target = graph.encodeNode(edge.getTarget());
                double weight = (double) edge.getWeight();
                if (weights[target] > weights[source] + weight) {
                    weights[target] = weights[source] + weight;
                    numberOfMoves[target] = numberOfMoves[source] + 1;
                    pathMemory[target] = source;
                    Optional<NodeWithPriority<Integer>> optionalNodeWithPriority = nodes.stream()
                            .filter(node -> node.equals(new NodeWithPriority<>(target)))
                            .findFirst();
                    if (optionalNodeWithPriority.isPresent()) {
                        optionalNodeWithPriority.get().setPriority(weights[target]);
                    } else {
                        nodes.offer(new NodeWithPriority<>(target, weights[target]));
                    }
                }
            }
        }
    }

    @Override
    public boolean nodeIsReachable(N target) {
        return true;
    }

    @Override
    public int getNumberOfMovesTo(N target) {
        return 0;
    }

    @Override
    public Iterable<N> getClosestCriticalNodesByCost(N source, int numberOfNeighbours) {
        throw new UnsupportedOperationException();
    }

}