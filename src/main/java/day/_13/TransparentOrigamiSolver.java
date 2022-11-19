package day._13;

import util.common.Solver;
import util.coordinate.Coordinate;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class TransparentOrigamiSolver extends Solver<Integer> {

    private final Origami origami;
    private List<Fold> folds = new ArrayList<>();

    public TransparentOrigamiSolver(String filename) {
        super(filename);
        origami = parseInput();
    }

    private Origami parseInput() {
        Set<Coordinate> dots = IntStream.range(0, puzzle.size())
                .takeWhile(i -> !puzzle.get(i).isEmpty())
                .mapToObj(i -> new Coordinate(puzzle.get(i)))
                .collect(Collectors.toSet());
        folds = IntStream.range(0, puzzle.size())
                .dropWhile(i -> !puzzle.get(i).contains("fold"))
                .mapToObj(i -> new Fold(puzzle.get(i)))
                .collect(Collectors.toList());
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
