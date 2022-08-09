package graphsystem.graph;

import java.util.Optional;

public interface Graph<N> {

    int getNumberOfNodes();

    int getNumberOfEdges();

    boolean connect(N node1, N mode2);

    Iterable<N> getNeighbours(N node);

    Optional<N> getNode(N node);

    Graph<N> copy();

    Iterable<N> getAllNodes();

    Integer encodeNode(N node);

    N decodeNode(Integer index);

    Optional<N> getStartNode();

    boolean setStartNode(N start);

    String toString();

}
