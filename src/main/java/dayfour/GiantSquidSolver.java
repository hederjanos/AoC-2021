package dayfour;

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
        List<Bingo> bingoArrayList = new ArrayList<>();
        int head = 2;
        while (head <= puzzle.size() - Bingo.BINGO_SIZE) {
            bingoArrayList.add(new Bingo(puzzle.subList(head, head + Bingo.BINGO_SIZE)));
            head += Bingo.BINGO_SIZE + 1;
        }
        return bingoArrayList;
    }

    @Override
    protected Integer solvePartOne() {
        return getResultByFirstWinner().orElse(null);
    }

    private Optional<Integer> getResultByFirstWinner() {
        Optional<Integer> result = Optional.empty();
        boolean isDrawInProgress = true;
        for (int i = 0; i < randomNumbers.size() && isDrawInProgress; i++) {
            Integer number = randomNumbers.get(i);
            for (Bingo bingo : bingoList) {
                bingo.markRandomNumberInBingoIfExists(number);
                if (bingo.isWin()) {
                    result = Optional.of(number * bingo.getSumOfUnMarkedNumbers());
                    isDrawInProgress = false;
                    break;
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
        Optional<Integer> result = Optional.empty();
        int winBingoCounter = 0;
        Integer lastRandomNumber = randomNumbers.get(0);
        Bingo lastBingo = bingoList.get(0);
        for (int i = 0; i < randomNumbers.size() && winBingoCounter < bingoList.size(); i++) {
            lastRandomNumber = randomNumbers.get(i);
            for (Bingo bingo : bingoList) {
                bingo.markRandomNumberInBingoIfExists(lastRandomNumber);
                if (!bingo.isAlreadyWon() && bingo.isWin()) {
                    bingo.setAlreadyWon();
                    lastBingo = bingo;
                    winBingoCounter++;
                }
            }
        }
        if (winBingoCounter == bingoList.size()) {
            result = Optional.of(lastRandomNumber * lastBingo.getSumOfUnMarkedNumbers());
        }
        return result;
    }
}
