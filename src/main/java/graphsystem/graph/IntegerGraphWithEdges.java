package graphsystem.graph;

import graphsystem.grid.GridCell;
import graphsystem.path.BreadthSearchPathFinder;
import graphsystem.path.PathFinder;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public final class IntegerGraphWithEdges implements GraphWithEdges<Integer, Integer> {

    private Integer start;
    private Integer[] order;
    private List<Edge<Integer, Integer>> edges;
    private Map<Integer, Set<Integer>> connections;

    public void transFormSimpleGridGraphByCriticalNodes(SimpleGridGraph graph, int numberOfNeighbours) {
        List<GridCell> criticalNodes = graph.getCriticalNodes();
        if (criticalNodes.isEmpty()) {
            throw new IllegalArgumentException();
        }
        initializeGraph(graph);
        PathFinder<GridCell, Integer> pathFinder = new BreadthSearchPathFinder<>(graph);
        for (int i = 0; i < criticalNodes.size(); i++) {
            GridCell startCell = criticalNodes.get(i);
            pathFinder.findAllPathsFromNode(startCell);
            int source = graph.encodeNode(startCell);
            order[i] = source;
            List<GridCell> closestCells = setClosestCells(startCell, criticalNodes, pathFinder, numberOfNeighbours);
            for (GridCell targetCell : closestCells) {
                if (!targetCell.equals(startCell) && pathFinder.nodeIsReachable(targetCell)) {
                    int target = graph.encodeNode(targetCell);
                    Integer weight = pathFinder.getNumberOfMovesTo(targetCell);
                    setEdge(source, target, weight);
                }
            }
        }
    }

    private void initializeGraph(SimpleGridGraph graph) {
        start = graph.encodeNode(graph.getStartNode());
        order = new Integer[graph.getCriticalNodes().size()];
        edges = new ArrayList<>();
        connections = new HashMap<>();
        for (GridCell criticalCell : graph.getCriticalNodes()) {
            Set<Integer> edgeIndexesForCell = new HashSet<>();
            connections.put(graph.encodeNode(criticalCell), edgeIndexesForCell);
        }
    }

    private List<GridCell> setClosestCells(GridCell cell, List<GridCell> criticalNodes, PathFinder<GridCell, Integer> pathFinder, int numberOfNeighbours) {
        List<GridCell> closestCells;
        if (numberOfNeighbours == criticalNodes.size()) {
            closestCells = criticalNodes;
        } else {
            closestCells = (List<GridCell>) pathFinder.getClosestCriticalNodesByCost(cell, numberOfNeighbours);
        }
        return closestCells;
    }

    public void transFormSimpleGridGraphByCriticalNodes(SimpleGridGraph graph) {
        this.transFormSimpleGridGraphByCriticalNodes(graph, graph.getCriticalNodes().size());
    }

    private void setEdge(int source, int target, Integer weight) {
        Edge<Integer, Integer> newEdge = new Edge<>(source, target, weight);
        int indexOfEdge;
        if (edges.contains(newEdge)) {
            indexOfEdge = edges.indexOf(newEdge);
        } else {
            indexOfEdge = edges.size();
            edges.add(newEdge);
        }
        connections.get(source).add(indexOfEdge);
        connections.get(target).add(indexOfEdge);
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
    public Iterable<Integer> getNeighbours(Integer node) {
        Set<Integer> edgeIndexes = connections.get(node);
        if (edgeIndexes == null) {
            throw new IllegalArgumentException();
        }
        return edgeIndexes.stream().map(edgeIndex -> {
            Edge<Integer, Integer> edge = edges.get(edgeIndex);
            if (edge.getSource().equals(node)) {
                return edge.getTarget();
            } else {
                return edge.getSource();
            }
        }).collect(Collectors.toUnmodifiableList());
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
    public Iterable<Edge<Integer, Integer>> getEdges(Integer node) {
        return connections.get(node).stream()
                .map(integer -> edges.get(integer))
                .map(Edge::copy)
                .collect(Collectors.toList());
    }

    @Override
    public Iterable<Edge<Integer, Integer>> getAllEdges(Integer node) {
        return edges.stream()
                .map(Edge::copy)
                .collect(Collectors.toList());
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
        return stringBuilder.toString().trim();
    }

}