package day._11;

import util.common.Solver;
import util.grid.IntegerGrid;

public class DumboOctopusSolver extends Solver<Integer> {

    private static final int MAX_STEP_COUNTER = 100;
    private final IntegerGrid energyMap;

    public DumboOctopusSolver(String filename) {
        super(filename);
        energyMap = parseInput();
    }

    private EnergyMap parseInput() {
        return new EnergyMap(puzzle, energyMap.convertContiguousIntegersToList(), false);
    }

    @Override
    protected Integer solvePartOne() {
        EnergyMap map = new EnergyMap(energyMap);
        int stepCounter = 0;
        while (stepCounter < MAX_STEP_COUNTER) {
            stepCounter = solveOneStep(map, stepCounter);
        }
        return map.getFlashCounter();
    }

    private int solveOneStep(EnergyMap map, int stepCounter) {
        map.increase(1);
        boolean flash;
        do {
            flash = map.flash(1);
        } while (flash);
        stepCounter++;
        return stepCounter;
    }

    @Override
    protected Integer solvePartTwo() {
        EnergyMap map = new EnergyMap(energyMap);
        int stepCounter = 0;
        while (!map.areAllFlashed()) {
            stepCounter = solveOneStep(map, stepCounter);
        }
        return stepCounter;
    }

}
