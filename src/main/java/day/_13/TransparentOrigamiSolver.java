package day._13;

import util.common.Solver;
import util.coordinate.Coordinate;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TransparentOrigamiSolver extends Solver<Integer> {

    private final Origami origami;
    private final List<Fold> folds = new ArrayList<>();

    public TransparentOrigamiSolver(String filename) {
        super(filename);
        this.origami = parseInput();
    }

    private Origami parseInput() {
        Set<Coordinate> dots = new HashSet<>();
        String line;
        int i = 0;
        while (!(line = puzzle.get(i)).isEmpty()) {
            dots.add(new Coordinate(line));
            i++;
        }
        i++;
        while (i < puzzle.size()) {
            folds.add(new Fold(puzzle.get(i)));
            i++;
        }
        return new Origami(dots);
    }

    @Override
    protected Integer solvePartOne() {
        origami.fold(folds.get(0));
        return origami.getCurrentDots().size();
    }

    @Override
    protected Integer solvePartTwo() {
        origami.reset();
        folds.forEach(origami::fold);
        System.out.println(origami);
        return null;
    }

}
