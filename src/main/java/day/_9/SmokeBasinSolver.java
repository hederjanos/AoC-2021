package day._9;

import util.common.Solver;
import util.grid.GridCell;
import util.grid.IntegerGrid;

import java.util.Comparator;
import java.util.List;

public class SmokeBasinSolver extends Solver<Integer> {

    private final IntegerGrid heightMap;
    private List<GridCell<Integer>> lowestLocations;

    public SmokeBasinSolver(String filename) {
        super(filename);
        heightMap = parseInput();
    }

    private HeightMap parseInput() {
        return new HeightMap(puzzle, heightMap.convertContiguousIntegersToList());
    }

    @Override
    protected Integer solvePartOne() {
        lowestLocations = ((HeightMap) heightMap).getLowestLocations();
        return lowestLocations.stream()
                .mapToInt(gridCell -> gridCell.getValue() + 1)
                .sum();
    }

    @Override
    protected Integer solvePartTwo() {
        return lowestLocations.stream()
                .map(((HeightMap) heightMap)::getSizeOfABasin)
                .sorted(Comparator.reverseOrder())
                .limit(3)
                .reduce(1, (a, b) -> a * b);
    }

}
