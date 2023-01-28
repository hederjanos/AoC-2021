package day._15;

import util.common.Solver;
import util.coordinate.Coordinate;
import util.grid.GridCell;
import util.grid.IntegerGrid;

import java.util.ArrayList;
import java.util.List;

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
        return expandMap().findLowestRiskPath().getSumRisk();
    }

    private RiskMap expandMap() {
        List<GridCell<Integer>> newBoard = new ArrayList<>();
        int newHeight = riskMap.getHeight() * 5;
        int newWidth = riskMap.getWidth() * 5;
        for (int i = 0; i < newHeight; i++) {
            int outerAdd = i / riskMap.getHeight();
            for (int j = 0; j < newWidth; j++) {
                updateNewBoard(newBoard, i, outerAdd, j);
            }
        }
        return new RiskMap(newBoard, newWidth, newHeight);
    }

    private void updateNewBoard(List<GridCell<Integer>> newBoard, int i, int outerAdd, int j) {
        int innerAdd = j / riskMap.getWidth();
        int x = j % riskMap.getWidth();
        int y = i % riskMap.getHeight();
        int boardValue = riskMap.getRiskAt(x, y);
        int newValue = boardValue + outerAdd + innerAdd;
        if (newValue > 9) {
            newValue -= 9;
        }
        newBoard.add(new GridCell<>(new Coordinate(j, i), newValue));
    }

}
