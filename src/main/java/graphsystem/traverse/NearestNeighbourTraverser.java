package graphsystem.traverse;

import graphsystem.graph.Graph;
import graphsystem.graph.SimpleGridGraph;
import graphsystem.path.BreadthSearchPathFinder;
import graphsystem.path.Path;
import graphsystem.path.PathFinder;

import java.util.List;

/**
 * @deprecated
 * Only works with SimpleGridGraph typed graph object which is small and connected!!!
 */
@Deprecated(forRemoval = true)
public class NearestNeighbourTraverser<N> extends Traverser<N, Integer>{

    public NearestNeighbourTraverser(Graph<N> graph) {
        if (!(graph instanceof SimpleGridGraph)) {
            throw new UnsupportedOperationException();
        }
        this.graph = graph;
        this.solution = new Path<>();
    }

    @Override
    public void explore() {
        int numberOfCriticalNodes = graph.getNumberOfCriticalNodes();
        PathFinder<N, Integer> pathFinder = new BreadthSearchPathFinder<>(graph);
        pathFinder.findAllPathsFromNode(graph.getStartNode());
        solution.addNode(graph.getStartNode(), 0);
        do {
            N parentNode = solution.get(solution.size() - 1);
            pathFinder.findAllPathsFromNode(parentNode);
            List<N> closestCellsToPrev = (List<N>) pathFinder.getClosestCriticalNodesByCost(parentNode, numberOfCriticalNodes, true);
            int i = 0;
            do {
                N closestNeighbourNode = closestCellsToPrev.get(i);
                if (!solution.contains(closestNeighbourNode)) {
                    solution.addNode(closestNeighbourNode, pathFinder.getNumberOfMovesTo(closestNeighbourNode));
                    break;
                }
                i++;
            } while (i < closestCellsToPrev.size());
        } while (solution.size() != numberOfCriticalNodes);
    }

    public Path<N, Integer> getSolution() {
        return solution;
    }

}