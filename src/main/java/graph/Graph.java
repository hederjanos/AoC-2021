package graph;

public interface Graph<N> {

    int getNumberOfNodes();

    int getNumberOfEdges();

    void connect(N node1, N mode2);

    Iterable<N> getNeighbours(N node);

    N getNode(N node);

    Graph<N> copy();

    Iterable<N> getAllNodes();

    String toString();

    Integer encodeNode(N node);

    N decodeNode(Integer index);

    boolean isStartNodeSet();

    N getStartNode();

    void setStartNode(N start);

}
