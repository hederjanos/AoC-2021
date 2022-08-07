package graph.path;

import graph.Graph;

public interface PathFinder<N> {

    void findAllPath(Graph<N> graph, N start);

    Path<N> pathTo(N target);

}
