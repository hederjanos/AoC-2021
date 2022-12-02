package day._15;

import util.coordinate.Coordinate;
import util.grid.GridCell;
import util.grid.IntegerGrid;

import java.util.*;
import java.util.function.Function;

public class RiskMap extends IntegerGrid {

    private final GridCell<Integer> start;
    private final GridCell<Integer> target;

    protected RiskMap(List<String> gridLines, Function<String, List<Integer>> tokenizer) {
        super(gridLines, tokenizer);
        start = board.get(calculateCellIndex(0, 0)).copy();
        target = board.get(calculateCellIndex(getWidth() - 1, getHeight() - 1)).copy();
    }

    public PathCell findLowestRiskPath() {
        int[] risks = new int[board.size()];
        Arrays.fill(risks, Integer.MAX_VALUE);
        int startIndex = calculateCellIndex(start.getCoordinate().getX(), start.getCoordinate().getY());
        risks[calculateCellIndex(start.getCoordinate().getX(), start.getCoordinate().getY())] = 0;
        Queue<PathCell> paths = new PriorityQueue<>();
        PathCell solution = new PathCell(start.getCoordinate(), risks[startIndex]);
        paths.offer(solution);
        while (!paths.isEmpty()) {
            PathCell currentPath = paths.poll();
            Coordinate currenCoordinate = currentPath.getCoordinate();
            if (currenCoordinate.equals(target.getCoordinate())) {
                solution = currentPath;
                break;
            }
            int coordinateIndex = calculateCellIndex(currenCoordinate.getX(), currenCoordinate.getY());
            for (Coordinate neighbour : currenCoordinate.getOrthogonalAdjacentCoordinates()) {
                if (isCoordinateInBounds(neighbour)) {
                    int neighbourIndex = calculateCellIndex(neighbour.getX(), neighbour.getY());
                    GridCell<Integer> neighbourCell = board.get(neighbourIndex);
                    int neighbourRisk = neighbourCell.getValue();
                    if (risks[neighbourIndex] > risks[coordinateIndex] + neighbourRisk) {
                        risks[neighbourIndex] = risks[coordinateIndex] + neighbourRisk;
                        Optional<PathCell> pathInQueue = paths.stream()
                                .filter(path -> path.getCoordinate().equals(neighbour))
                                .findFirst();
                        if (pathInQueue.isPresent()) {
                            pathInQueue.get().setSumRisk(risks[neighbourIndex]);
                        } else {
                            paths.offer(new PathCell(neighbour, risks[neighbourIndex]));
                        }
                    }
                }
            }
        }
        return solution;
    }

}
