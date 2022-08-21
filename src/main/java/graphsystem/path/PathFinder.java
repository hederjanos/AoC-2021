package graphsystem.path;

public interface PathFinder<N> {

    void findAllPathsFromDefaultStartNode();

    void findAllPathsFromNode(N start);

    Path<N> pathTo(N target);

}
