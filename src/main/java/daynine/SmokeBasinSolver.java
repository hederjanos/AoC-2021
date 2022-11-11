package daynine;

import util.common.Solver;

import java.util.stream.Collectors;

public class SmokeBasinSolver extends Solver<Integer> {

    private HeightMap heightMap;

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
        return null;
    }

    @Override
    protected Integer solvePartTwo() {
        return null;
    }

}
