package day4;

import util.Solver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class GiantSquidSolver extends Solver<Integer> {

    public GiantSquidSolver(String filename) {
        super(filename);
    }

    @Override
    protected Integer solvePartOne() {
        List<Integer> randomNumbers = parseRandomNumbers();
        List<Bingo> bingoList = parseBingos();
        return getResult(randomNumbers, bingoList).orElse(null);
    }

    private Optional<Integer> getResult(List<Integer> randomNumbers, List<Bingo> bingoList) {
        Optional<Integer> result = Optional.empty();
        outer:
        for (Integer number : randomNumbers) {
            for (Bingo bingo : bingoList) {
                bingo.markRandomNumberInBingoIfExists(number);
                if (bingo.isWin()) {
                    result = Optional.of(number * bingo.getSumOfUnMarkedNumbers());
                    break outer;
                }
            }
        }
        return result;
    }

    private List<Integer> parseRandomNumbers() {
        return Arrays.stream(puzzle.get(0).split(","))
                .map(Integer::parseInt).collect(Collectors.toList());
    }

    private List<Bingo> parseBingos() {
        List<Bingo> bingoList = new ArrayList<>();
        int head = 2;
        while (head <= puzzle.size() - Bingo.bingoSize) {
            bingoList.add(new Bingo(puzzle.subList(head, head + Bingo.bingoSize)));
            head += Bingo.bingoSize + 1;
        }
        return bingoList;
    }

    @Override
    protected Integer solvePartTwo() {
        return null;
    }
}
