package day._15;

import util.common.Solver;
import util.grid.IntegerGrid;

public class ChitonSolver extends Solver<Integer> {

    private final RiskMap riskMap;

    public ChitonSolver(String filename) {
        super(filename);
        riskMap = parseInput();
    }

    private RiskMap parseInput() {
        return new RiskMap(puzzle, IntegerGrid.convertContiguousIntegersToList());
    }

    @Override
    protected Integer solvePartOne() {
        return riskMap.findLowestRiskPath().getSumRisk();
    }

    @Override
    protected Integer solvePartTwo() {
        return null;
    }

}
