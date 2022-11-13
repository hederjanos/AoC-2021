package day._11;

import util.common.Solver;
import util.grid.IntegerGrid;

import java.util.stream.Collectors;

public class DumboOctopusSolver extends Solver<Integer> {

    private final int MAX_STEP_COUNTER = 100;
    private final IntegerGrid energyMap;

    public DumboOctopusSolver(String filename) {
        super(filename);
        this.energyMap = parseInput();
    }

    private EnergyMap parseInput() {
        return new EnergyMap(puzzle,
                s -> s.chars()
                        .mapToObj(c -> (char) c)
                        .map(Character::getNumericValue)
                        .collect(Collectors.toList()),
                false);
    }

    @Override
    protected Integer solvePartOne() {
        EnergyMap map = (EnergyMap) energyMap;
        int stepCounter = 0;
        while (stepCounter < MAX_STEP_COUNTER) {
            map.increase(1);
            boolean flash;
            do {
                flash = map.flash(1);
            } while (flash);
            stepCounter++;
        }
        return map.getFlashCounter();
    }

    @Override
    protected Integer solvePartTwo() {
        return null;
    }

}
