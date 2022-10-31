package graphsystem.traverse;

import graphsystem.graph.Graph;
import graphsystem.path.Path;

public abstract class Traverser<N, W extends Number> {

    protected Graph<N> graph;
    protected Path<N, W> solution;

    public abstract void explore();

}
