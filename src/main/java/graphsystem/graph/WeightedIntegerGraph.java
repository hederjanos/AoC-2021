package graphsystem.graph;

import graphsystem.grid.GridCell;
import graphsystem.path.BreadthSearchPathFinder;
import graphsystem.path.PathFinder;

import java.util.*;
import java.util.stream.Collectors;

public final class WeightedIntegerGraph implements GraphWithEdges<Integer, Double> {

    private Integer start;
    private List<Edge<Integer, Double>> edges;
    private Map<Integer, List<Integer>> connections;

    public WeightedIntegerGraph(Graph<?> graph) {
        if (graph == null) {
            throw new IllegalArgumentException();
        }
        if (graph instanceof SimpleGridGraph) {
            SimpleGridGraph gridGraph = (SimpleGridGraph) graph;
            List<GridCell> mustBeVisitedCells = gridGraph.getMustBeVisitedCells();
            if (mustBeVisitedCells.isEmpty()) {
                throw new IllegalArgumentException();
            }
            start = gridGraph.encodeNode(gridGraph.getStartNode());
            edges = new ArrayList<>();
            connections = new HashMap<>();
            PathFinder<GridCell> pathFinder = new BreadthSearchPathFinder<>(gridGraph);
            for (GridCell startCell : mustBeVisitedCells) {
                pathFinder.findAllPathsFromNode(startCell);
                int node1 = gridGraph.encodeNode(startCell);
                for (GridCell targetCell : mustBeVisitedCells) {
                    if (!targetCell.equals(startCell) && pathFinder.nodeIsReachable(targetCell)) {
                        int node2 = gridGraph.encodeNode(targetCell);
                        Double weight = (double) pathFinder.getNumberOfMovesTo(targetCell);
                        setEdge(node1, node2, weight);
                    }
                }
            }
        }
    }

    private void setEdge(int node1, int node2, Double weight) {
        Edge<Integer, Double> newEdge = new Edge<>(node1, node2, weight);
        int indexOfEdge;
        if (edges.contains(newEdge)) {
            indexOfEdge = edges.indexOf(newEdge);
        } else {
            indexOfEdge = edges.size();
            edges.add(newEdge);
        }
        List<Integer> edgeIndexes;
        if (!connections.containsKey(node1)) {
            edgeIndexes = new ArrayList<>();
            connections.put(node1, edgeIndexes);
        } else {
            edgeIndexes = connections.get(node1);
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
        return node;
    }

    @Override
    public Integer decodeNode(Integer index) {
        return index;
    }

    @Override
    public Integer getStartNode() {
        return start;
    }

    @Override
    public Iterable<Edge<Integer, Double>> getEdges(Integer node) {
        return connections.get(node).stream()
                .map(integer -> edges.get(integer))
                .collect(Collectors.toList());
    }

}
