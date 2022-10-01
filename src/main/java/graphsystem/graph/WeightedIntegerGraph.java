package graphsystem.graph;

import graphsystem.grid.GridCell;
import graphsystem.path.BreadthSearchPathFinder;
import graphsystem.path.PathFinder;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public final class WeightedIntegerGraph implements GraphWithEdges<Integer, Double> {

    private Integer start;
    private Integer[] order;
    private List<Edge<Integer, Double>> edges;
    private Map<Integer, List<Integer>> connections;

    public void transFormSimpleGridGraphByCriticalNodes(SimpleGridGraph graph, int numberOfNeighbours) {
        List<GridCell> mustBeVisitedCells = graph.getCriticalNodes();
        if (mustBeVisitedCells.isEmpty()) {
            throw new IllegalArgumentException();
        }
        start = graph.encodeNode(graph.getStartNode());
        order = new Integer[graph.getCriticalNodes().size()];
        edges = new ArrayList<>();
        connections = new HashMap<>();
        PathFinder<GridCell> pathFinder = new BreadthSearchPathFinder<>(graph);
        for (int i = 0; i < mustBeVisitedCells.size(); i++) {
            GridCell startCell = mustBeVisitedCells.get(i);
            pathFinder.findAllPathsFromNode(startCell);
            int source = graph.encodeNode(startCell);
            order[i] = source;
            List<GridCell> closestCells;
            if (numberOfNeighbours == mustBeVisitedCells.size()) {
                closestCells = mustBeVisitedCells;
            } else {
                closestCells = (List<GridCell>) pathFinder.getClosestCriticalNodesByCost(startCell, numberOfNeighbours);
            }
            for (GridCell targetCell : closestCells) {
                if (!targetCell.equals(startCell) && pathFinder.nodeIsReachable(targetCell)) {
                    int target = graph.encodeNode(targetCell);
                    Double weight = (double) pathFinder.getNumberOfMovesTo(targetCell);
                    setEdge(source, target, weight);
                }
            }
        }
    }

    public void transFormSimpleGridGraphByCriticalNodes(SimpleGridGraph graph) {
        this.transFormSimpleGridGraphByCriticalNodes(graph, graph.getCriticalNodes().size());
    }

    private void setEdge(int source, int target, Double weight) {
        Edge<Integer, Double> newEdge = new Edge<>(source, target, weight);
        int indexOfEdge;
        if (edges.contains(newEdge)) {
            indexOfEdge = edges.indexOf(newEdge);
        } else {
            indexOfEdge = edges.size();
            edges.add(newEdge);
        }
        List<Integer> edgeIndexes;
        if (!connections.containsKey(source)) {
            edgeIndexes = new ArrayList<>();
            connections.put(source, edgeIndexes);
        } else {
            edgeIndexes = connections.get(source);
        }
        edgeIndexes.add(indexOfEdge);
    }

    @Override
    public int getNumberOfNodes() {
        return connections.size();
    }

    @Override
    public int getNumberOfEdges() {
        return edges.size();
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Number of nodes: ")
                .append(connections.size())
                .append(". Number of edges: ")
                .append(edges.size())
                .append(".\n");
        connections.forEach((key, value) -> {
            stringBuilder.append("Node=").append(key).append(": ");
            for (Integer index : value) {
                stringBuilder.append(edges.get(index).toString()).append(", ");
            }
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
            stringBuilder.append("\n");
        });
        return stringBuilder.toString();
    }

    @Override
    public Iterable<Integer> getNeighbours(Integer node) {
        return connections.get(node);
    }

    @Override
    public Optional<Integer> getNode(Integer node) {
        Optional<Integer> optional = Optional.empty();
        if (connections.containsKey(node)) {
            optional = Optional.of(node);
        }
        return optional;
    }

    @Override
    public boolean nodeIsPassable(Integer node) {
        return true;
    }

    @Override
    public Graph<Integer> copy() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterable<Integer> getAllNodes() {
        return connections.keySet();
    }

    @Override
    public Integer encodeNode(Integer node) {
        OptionalInt optionalIndex = IntStream.range(0, order.length)
                .filter(i -> Objects.equals(order[i], node))
                .findFirst();

        if (optionalIndex.isEmpty()) {
            throw new IllegalArgumentException();
        }
        return optionalIndex.getAsInt();
    }

    @Override
    public Integer decodeNode(Integer index) {
        if (index < 0 || index >= order.length) {
            throw new IllegalArgumentException();
        }
        return order[index];
    }

    @Override
    public Integer getStartNode() {
        return start;
    }

    @Override
    public Iterable<Integer> getCriticalNodes() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterable<Edge<Integer, Double>> getEdges(Integer node) {
        return connections.get(node).stream()
                .map(integer -> edges.get(integer))
                .collect(Collectors.toList());
    }

}
