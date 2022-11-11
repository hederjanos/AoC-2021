package daynine;

import util.coordinate.Direction;
import util.grid.GridCell;
import util.grid.IntegerGrid;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.IntStream;

public class HeightMap extends IntegerGrid {

    private final boolean fourWayDirection;

    protected HeightMap(List<String> gridLines, Function<String, List<Integer>> tokenizer) {
        this(gridLines, tokenizer, true);
    }

    protected HeightMap(List<String> gridLines, Function<String, List<Integer>> tokenizer, boolean fourWayDirection) {
        super(gridLines, tokenizer);
        this.fourWayDirection = fourWayDirection;
    }

    public List<GridCell<Integer>> getLowestLocations() {
        List<GridCell<Integer>> lowestLocations = new ArrayList<>();
        IntStream.range(0, height)
                .forEach(i ->
                        IntStream.range(0, width)
                                .forEach(j -> {
                                    GridCell<Integer> currentLocation = board[i][j];
                                    if (isALocalMinimum(i, j, currentLocation)) {
                                        lowestLocations.add(currentLocation);
                                    }
                                }));
        return lowestLocations;
    }

    private boolean isALocalMinimum(int i, int j, GridCell<Integer> currentLocation) {
        boolean isALocalMinimum = true;
        for (Direction direction : Direction.values()) {
            if (!fourWayDirection || direction.ordinal() % 2 == 0) {
                GridCell<Integer> currentNeighbour;
                int newI = i + direction.getX();
                int newJ = j + direction.getY();
                if (areIndexesInBounds(newI, newJ)) {
                    currentNeighbour = board[newI][newJ];
                    if (currentNeighbour.getValue() <= currentLocation.getValue()) {
                        isALocalMinimum = false;
                        break;
                    }
                }
            }
        }
        return isALocalMinimum;
    }

    private boolean areIndexesInBounds(int i, int j) {
        return i < height && i >= 0 && j < width && j >= 0;
    }

    public void printHeightMap() {
        Arrays.stream(board).forEach(gridCells -> {
            Arrays.stream(gridCells)
                    .map(GridCell::getValue)
                    .forEach(System.out::print);
            System.out.println();
        });
    }

}
