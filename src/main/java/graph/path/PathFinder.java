package graph.path;

import graph.Graph;

public interface PathFinder<V> {

    void findAllPath(Graph<V> graph, V start);

    Path<V> pathTo(V target);

}
