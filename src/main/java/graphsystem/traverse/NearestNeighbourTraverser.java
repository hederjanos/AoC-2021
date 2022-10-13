package graphsystem.traverse;

import graphsystem.graph.Graph;
import graphsystem.graph.SimpleGridGraph;
import graphsystem.grid.GridCell;
import graphsystem.path.BreadthSearchPathFinder;
import graphsystem.path.Path;
import graphsystem.path.PathFinder;

import java.util.List;

public class NearestNeighbourTraverser<N> {

    Path<N, Integer> solution = new Path<>();

    public NearestNeighbourTraverser(Graph<N> graph) {
        if (!(graph instanceof SimpleGridGraph)) {
            throw new IllegalArgumentException();
        }
        //TODO Make general
        PathFinder<N, Integer> pathFinder = new BreadthSearchPathFinder<>(graph);
        pathFinder.findAllPathsFromNode(graph.getStartNode());
        N startNode = graph.getStartNode();
        N furthestNodeFromStart = pathFinder.getFurthestNode(startNode);
        int numberOfCriticalNodes = graph.getNumberOfCriticalNodes();
        pathFinder.findAllPathsFromNode(furthestNodeFromStart);
        List<N> closestCellsToFurthest = (List<N>) pathFinder.getClosestCriticalNodesByCost(furthestNodeFromStart, numberOfCriticalNodes, true);
        solution.addNode(graph.getStartNode(), 0);
        while (solution.size() != numberOfCriticalNodes) {
            N parent = solution.get(solution.size() - 1);
            pathFinder.findAllPathsFromNode(parent);
            List<N> closestCellsToPrev = (List<N>) pathFinder.getClosestCriticalNodesByCost(parent, numberOfCriticalNodes, true);

            N neighbour1;
            int i = 0;
            do {
                neighbour1 = closestCellsToPrev.get(i);
                i++;
            } while (i < closestCellsToPrev.size() && solution.contains(neighbour1));

            closestCellsToPrev.sort((n1, n2) -> Integer.compare(closestCellsToFurthest.indexOf(n2), closestCellsToFurthest.indexOf(n1)));

            N neighbour2;
            i = 0;
            do {
                neighbour2 = closestCellsToPrev.get(i);
                i++;
            } while (i < closestCellsToPrev.size() && solution.contains(neighbour2));

            int distance1 = ((GridCell) parent).getPosition().calculateDistance(((GridCell) neighbour1).getPosition());
            int distance2 = ((GridCell) parent).getPosition().calculateDistance(((GridCell) neighbour2).getPosition());

            if (distance1 < distance2 && Math.abs(distance1 - distance2) < numberOfCriticalNodes) {
                solution.addNode(neighbour1, pathFinder.getNumberOfMovesTo(neighbour1));
            } else {
                solution.addNode(neighbour2, pathFinder.getNumberOfMovesTo(neighbour2));
            }
        }
    }

    public Path<N, Integer> getSolution() {
        return solution;
    }

}