package day._12;

import util.common.Solver;

import java.util.*;
import java.util.stream.Collectors;

public class PassagePathingSolver extends Solver<Integer> {

    private final CaveSystem caveSystem;

    public PassagePathingSolver(String filename) {
        super(filename);
        caveSystem = parseInput();
    }

    private CaveSystem parseInput() {
        return new CaveSystem(puzzle, line -> Arrays.stream(line.split("-")).collect((Collectors.toList())));
    }

    @Override
    protected Integer solvePartOne() {
       return solve(true);
    }

    private Integer solve(boolean withRulesPartOne) {
        int numberOfPath = 0;
        Deque<List<Cave>> stack = new ArrayDeque<>();
        Cave start = caveSystem.getStart();
        List<Cave> startList = new ArrayList<>();
        startList.add(start);
        stack.push(startList);
        while (!stack.isEmpty()) {
            List<Cave> caves = stack.pop();
            List<Cave> neighbours = caveSystem.getNeighboursOfCave(caves.get(caves.size() - 1).hashCode());
            for (Cave cave : neighbours) {
                List<Cave> copy = new ArrayList<>(caves);
                if (caveSystem.getEnd().equals(cave)) {
                    numberOfPath++;
                    continue;
                }
                if (withRulesPartOne ? compliesWithRulesPartOne(cave, copy) : compliesWithRulesPartTwo(cave, copy)) {
                    copy.add(cave);
                    stack.push(copy);
                }
            }
        }
        return numberOfPath;
    }

    private boolean compliesWithRulesPartOne(Cave cave, List<Cave> copy) {
        return cave.isBig() || !copy.contains(cave);
    }

    private boolean compliesWithRulesPartTwo(Cave cave, List<Cave> copy) {
        return !caveSystem.getStart().equals(cave) &&
               (cave.isBig() || !copy.contains(cave) || (isCaveOnceIn(cave, copy) && areAllSmallCavesOnceIn(copy)));
    }

    private boolean isCaveOnceIn(Cave cave, List<Cave> copy) {
        return copy.stream().filter(c -> c.equals(cave)).count() == 1;
    }

    private boolean areAllSmallCavesOnceIn(List<Cave> copy) {
        boolean areAllSmallCavesOnceIn = true;
        Map<Cave, Integer> caveMap = new HashMap<>();
        List<Cave> smallCaves = copy.stream().filter(cave -> !cave.isBig()).collect(Collectors.toList());
        for (Cave cave : smallCaves) {
            if (!caveMap.containsKey(cave)) {
                caveMap.put(cave, 1);
            } else {
                areAllSmallCavesOnceIn = false;
                break;
            }
        }
        return areAllSmallCavesOnceIn;
    }

    @Override
    protected Integer solvePartTwo() {
        return solve(false);
    }

}
