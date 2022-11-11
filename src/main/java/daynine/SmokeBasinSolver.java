package daynine;

import util.common.Solver;
import util.grid.GridCell;
import util.grid.IntegerGrid;

import java.util.List;
import java.util.stream.Collectors;

public class SmokeBasinSolver extends Solver<Integer> {

    private final IntegerGrid heightMap;

    public SmokeBasinSolver(String filename) {
        super(filename);
        this.heightMap = parseInput();
    }

    private HeightMap parseInput() {
        return new HeightMap(puzzle,
                s -> s.chars()
                        .mapToObj(c -> (char) c)
                        .map(Character::getNumericValue)
                        .collect(Collectors.toList()));
    }

    @Override
    protected Integer solvePartOne() {
        List<GridCell<Integer>> lowestLocations = ((HeightMap) heightMap).getLowestLocations();
        return lowestLocations.stream()
                .mapToInt(gridCell -> gridCell.getValue() + 1)
                .sum();
    }

    @Override
    protected Integer solvePartTwo() {
        return null;
    }

}
