package graphsystem.graph;

import graphsystem.grid.GridCell;
import graphsystem.path.BreadthSearchPathFinder;
import graphsystem.path.PathFinder;

import java.util.*;

public class WeightedIntegerGraph implements Graph<Integer> {

    private Map<Integer, List<Edge<Integer, Integer>>> connections;

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
            PathFinder<GridCell> pathFinder = new BreadthSearchPathFinder<>(gridGraph);
            connections = new HashMap<>();
            for (GridCell startCell : mustBeVisitedCells) {
                pathFinder.findAllPathsFromNode(startCell);
                int startIndex = gridGraph.encodeNode(startCell);
                connections.put(startIndex, new ArrayList<>());
                for (GridCell targetCell : mustBeVisitedCells) {
                    if (!targetCell.equals(startCell) && pathFinder.nodeIsReachable(targetCell)) {
                        int endIndex = gridGraph.encodeNode(targetCell);
                        int weight = pathFinder.getNumberOfMovesTo(targetCell);
                        Edge<Integer, Integer> edge = new Edge<>(startIndex, endIndex, weight);
                        connections.get(startIndex).add(edge);
                    }
                }
            }
        }
    }

    @Override
    public int getNumberOfNodes() {
        return connections.size();
    }

    @Override
    public int getNumberOfEdges() {
        return 0;
    }

    @Override
    public boolean connect(Integer node1, Integer mode2) {
        return false;
    }

    @Override
    public Iterable<Integer> getNeighbours(Integer node) {
        return null;
    }

    @Override
    public Optional<Integer> getNode(Integer node) {
        return Optional.empty();
    }

    @Override
    public boolean nodeIsPassable(Integer node) {
        return false;
    }

    @Override
    public Graph<Integer> copy() {
        return null;
    }

    @Override
    public Iterable<Integer> getAllNodes() {
        return null;
    }

    @Override
    public Integer encodeNode(Integer node) {
        return null;
    }

    @Override
    public Integer decodeNode(Integer index) {
        return null;
    }

    @Override
    public Optional<Integer> getStartNode() {
        return Optional.empty();
    }

    @Override
    public boolean setStartNode(Integer start) {
        return false;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Number of nodes: ")
                .append(connections.size())
                .append(". Number of edges: ")
                .append("0")
                .append(".\n");
        connections.forEach((key, value) -> {
            stringBuilder.append("Node=").append(key).append(": ");
            for (Edge<Integer, Integer> edge : value) {
                stringBuilder.append(edge.toString()).append(", ");
            }
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
            stringBuilder.append("\n");
        });
        return stringBuilder.toString();
    }
}
