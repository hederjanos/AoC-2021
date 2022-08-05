package graph;

public interface Graph<V> {

    int getNumberOfVertices();

    int getNumberOfEdges();

    void connect(V vertex1, V vertex2);

    Iterable<V> getNeighbours(V vertex);

    V getVertex(V vertex);

    Graph<V> copy();

    Iterable<V> getAllVertices();

    String toString();

    Integer encodeVertex(V vertex);

    V decodeVertex(Integer index);

    boolean isStartSet();

    V getStart();

    void setStart(V start);

}
