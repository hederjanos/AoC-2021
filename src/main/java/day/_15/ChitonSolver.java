package day._15;

import util.common.Solver;
import util.coordinate.Coordinate;
import util.grid.GridCell;
import util.grid.IntegerGrid;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

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
        List<GridCell<Integer>> board = riskMap.getBoard();
        List<GridCell<Integer>> newBoard = new ArrayList<>();
        int newHeight = riskMap.getHeight() * 5;
        int newWidth = riskMap.getWidth() * 5;
        IntStream.range(0, newHeight)
                .forEach(i -> {
                    int outerAdd = i / riskMap.getHeight();
                    IntStream.range(0, newWidth)
                            .forEach(j -> {
                                int innerAdd = j / riskMap.getWidth();
                                int x = j % riskMap.getWidth();
                                int y = i % riskMap.getHeight();
                                int boardValue = board.get(riskMap.calculateCellIndex(x, y)).getValue();
                                int newValue = boardValue + outerAdd + innerAdd;
                                if (newValue > 9) {
                                    newValue -= 9;
                                }
                                newBoard.add(new GridCell<>(new Coordinate(j, i), newValue));
                            });
                });
        return new RiskMap(newBoard, newWidth, newHeight);
    }

}
