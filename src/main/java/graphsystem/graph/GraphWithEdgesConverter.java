package graphsystem.graph;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GraphWithEdgesConverter<N, W extends Number> {

    private final boolean[] marked;
    private double weight;
    private final Set<Edge<N, W>> edges;
    private final Queue<Edge<N, W>> priorityQueue;

    public GraphWithEdgesConverter(Graph<N> graph) {
        if (!(graph instanceof GraphWithEdges)) {
            throw new IllegalArgumentException();
        }
        marked = new boolean[graph.getNumberOfNodes()];
        edges = new HashSet<>();
        priorityQueue = new PriorityQueue<>();

        explore(graph, graph.getStartNode());

        while (!priorityQueue.isEmpty()) {
            Edge<N, W> edge = priorityQueue.poll();
            N source = edge.getSource();
            N target = edge.getTarget();
            if (marked[graph.encodeNode(source)] && marked[graph.encodeNode(target)]) {
                continue;
            }
            edges.add(edge);
            weight += edge.getWeight().doubleValue();
            if (!marked[graph.encodeNode(source)]) {
                explore(graph, source);
            }
            if (!marked[graph.encodeNode(target)]) {
                explore(graph, target);
            }
        }
    }

    private void explore(Graph<N> graph, N node) {
        marked[graph.encodeNode(node)] = true;
        N target;
        for (Edge<N, W> edge : ((GraphWithEdges<N, W>) graph).getEdges(node)) {
            if (edge.getSource().equals(node)) {
                target = edge.getTarget();
            } else {
                target = edge.getSource();
            }
            if (!marked[graph.encodeNode(target)]) {
                priorityQueue.add(edge);
            }
        }
    }

    public Map<N, Integer> getJunctionMap() {
        return edges.stream()
                .flatMap(edge -> Stream.of(edge.getSource(), edge.getTarget()))
                .collect(Collectors.toMap(
                        node -> node,
                        node -> 1,
                        (currentOccurrence, nextOccurrence) -> currentOccurrence + 1)
                );
    }

    public double getWeight() {
        return weight;
    }

    public Iterable<Edge<N, W>> getMinimumWeightEdges() {
        return edges.stream()
                .map(Edge::copy)
                .collect(Collectors.toList());
    }

}