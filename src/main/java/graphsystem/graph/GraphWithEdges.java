package graphsystem.graph;

public interface GraphWithEdges<N, W extends Number> extends Graph<N> {

    Iterable<Edge<N, W>> getEdges(N node);
}
