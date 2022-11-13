package day._11;

import util.coordinate.Coordinate;
import util.grid.GridCell;
import util.grid.IntegerGrid;

import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Function;

public class EnergyMap extends IntegerGrid {

    private static final int MAX_ENERGY = 9;
    private static final int MIN_ENERGY = 0;
    private int flashCounter;

    protected EnergyMap(List<String> gridLines, Function<String, List<Integer>> tokenizer, boolean fourWayDirection) {
        super(gridLines, tokenizer, fourWayDirection);
    }

    public void increase(int increment) {
        board.forEach(gridCell -> gridCell.setValue(gridCell.getValue() + increment));
    }

    public boolean flash(int increment) {
        AtomicBoolean nextFlash = new AtomicBoolean(false);
        board.forEach(gridCell -> {
            if (gridCell.getValue() > MAX_ENERGY) {
                flashCounter++;
                gridCell.setValue(0);
                Set<Coordinate> neighbourCoordinates;
                if (fourWayDirection) {
                    neighbourCoordinates = gridCell.getCoordinate().getOrthogonalAdjacentCoordinates();
                } else {
                    neighbourCoordinates = gridCell.getCoordinate().getAdjacentCoordinates();
                }
                neighbourCoordinates.forEach(coordinate -> {
                    if (isCoordinateInBounds(coordinate)) {
                        GridCell<Integer> currentNeighbour = board.get(calculateCellIndex(coordinate.getX(), coordinate.getY()));
                        if (currentNeighbour.getValue() != MIN_ENERGY) {
                            currentNeighbour.setValue(currentNeighbour.getValue() + increment);
                            if (currentNeighbour.getValue() > MAX_ENERGY) {
                                nextFlash.set(true);
                            }
                        }
                    }
                });
            }
        });
        return nextFlash.get();
    }

    public int getFlashCounter() {
        return flashCounter;
    }

}
