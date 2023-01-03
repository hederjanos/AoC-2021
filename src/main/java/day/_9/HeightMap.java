package day._9;

import util.coordinate.Coordinate;
import util.grid.GridCell;
import util.grid.IntegerGrid;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class HeightMap extends IntegerGrid {

    private static final int MAX_HEIGHT = 9;

    protected HeightMap(List<String> gridLines, Function<String, List<Integer>> tokenizer) {
        super(gridLines, tokenizer);
    }

    public List<GridCell<Integer>> getLowestLocations() {
        return board.stream()
                .filter(this::isALocalMinimum)
                .collect(Collectors.toUnmodifiableList());
    }

    private boolean isALocalMinimum(GridCell<Integer> currentLocation) {
        boolean isALocalMinimum = true;
        Set<Coordinate> neighbourCoordinates;
        if (fourWayDirection) {
            neighbourCoordinates = currentLocation.getCoordinate().getOrthogonalAdjacentCoordinates();
        } else {
            neighbourCoordinates = currentLocation.getCoordinate().getAdjacentCoordinates();
        }
        for (Coordinate coordinate : neighbourCoordinates) {
            if (isCoordinateInBounds(coordinate)) {
                GridCell<Integer> currentNeighbour = board.get(calculateCellIndex(coordinate.getX(), coordinate.getY()));
                if (currentNeighbour.getValue() <= currentLocation.getValue()) {
                    isALocalMinimum = false;
                    break;
                }
            }
        }
        return isALocalMinimum;
    }

    public int getSizeOfABasin(GridCell<Integer> gridCell) {
        Set<GridCell<Integer>> visitedCells = new HashSet<>();
        int size = 0;
        Queue<GridCell<Integer>> queue = new ArrayDeque<>();
        visitedCells.add(gridCell);
        queue.offer(gridCell);
        size++;
        while (!queue.isEmpty()) {
            GridCell<Integer> lastCell = queue.poll();
            for (Coordinate coordinate : lastCell.getCoordinate().getOrthogonalAdjacentCoordinates()) {
                if (isCoordinateInBounds(coordinate)) {
                    GridCell<Integer> currentNeighbour = board.get(calculateCellIndex(coordinate.getX(), coordinate.getY()));
                    if (!visitedCells.contains(currentNeighbour) && currentNeighbour.getValue() != MAX_HEIGHT) {
                        visitedCells.add(currentNeighbour);
                        size++;
                        queue.offer(currentNeighbour);
                    }
                }
            }
        }
        return size;
    }

}
