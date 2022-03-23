package day4;

import util.Solver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class GiantSquidSolver extends Solver<Integer> {

    private final List<Integer> randomNumbers;
    private final List<Bingo> bingoList;

    public GiantSquidSolver(String filename) {
        super(filename);
        this.randomNumbers = parseRandomNumbers();
        this.bingoList = parseBingos();
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
    protected Integer solvePartOne() {
        return getResultByFirstWinner().orElse(null);
    }

    private Optional<Integer> getResultByFirstWinner() {
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

    @Override
    protected Integer solvePartTwo() {
        bingoList.forEach(Bingo::resetBingo);
        return getResultByLastWinner().orElse(null);
    }

    private Optional<Integer> getResultByLastWinner() {
        Optional<Integer> result;
        int winBingoCounter = 0;
        outer:
        while (true) {
            for (Integer number : randomNumbers) {
                for (Bingo bingo : bingoList) {
                    bingo.markRandomNumberInBingoIfExists(number);
                    if (bingo.isWin()) {
                        winBingoCounter++;
                    }
                    if (winBingoCounter == bingoList.size()) {
                        bingo.printBingoNumbers();
                        result = Optional.of(number * bingo.getSumOfUnMarkedNumbers());
                        break outer;
                    }
                }
            }
        }
        return result;
    }
}
