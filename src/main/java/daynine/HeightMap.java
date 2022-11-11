package daynine;

import util.coordinate.Coordinate;
import util.grid.GridCell;
import util.grid.IntegerGrid;

import java.util.ArrayDeque;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public class HeightMap extends IntegerGrid {

    private final boolean fourWayDirection;
    private static final int MAX_HEIGHT = 9;

    protected HeightMap(List<String> gridLines, Function<String, List<Integer>> tokenizer) {
        this(gridLines, tokenizer, true);
    }

    protected HeightMap(List<String> gridLines, Function<String, List<Integer>> tokenizer, boolean fourWayDirection) {
        super(gridLines, tokenizer);
        this.fourWayDirection = fourWayDirection;
    }

    public List<GridCell<Integer>> getLowestLocations() {
        return board.stream()
                .filter(this::isALocalMinimum)
                .collect(Collectors.toList());
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
        int size = 0;
        Queue<GridCell<Integer>> queue = new ArrayDeque<>();
        gridCell.setMarked();
        queue.offer(gridCell);
        size++;
        while (!queue.isEmpty()) {
            GridCell<Integer> lastCell = queue.poll();
            for (Coordinate coordinate : lastCell.getCoordinate().getOrthogonalAdjacentCoordinates()) {
                if (isCoordinateInBounds(coordinate)) {
                    GridCell<Integer> currentNeighbour = board.get(calculateCellIndex(coordinate.getX(), coordinate.getY()));
                    if (!currentNeighbour.isMarked() && currentNeighbour.getValue() != MAX_HEIGHT) {
                        currentNeighbour.setMarked();
                        size++;
                        queue.offer(currentNeighbour);
                    }
                }
            }
        }
        return size;
    }

}
