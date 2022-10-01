package graphsystem.graph;

import java.util.Optional;

public interface Graph<N> {

    int getNumberOfNodes();

    int getNumberOfEdges();

    Iterable<N> getNeighbours(N node);

    Optional<N> getNode(N node);

    boolean nodeIsPassable(N node);

    Graph<N> copy();

    Iterable<N> getAllNodes();

    Integer encodeNode(N node);

    N decodeNode(Integer index);

    N getStartNode();

    Iterable<N> getCriticalNodes();

}
