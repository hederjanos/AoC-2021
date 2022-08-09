package graphsystem.path;

import graphsystem.graph.Graph;

public interface PathFinder<N> {

    void findAllPath(Graph<N> graph);

    Path<N> pathTo(N target);

}
