package dayfour;

import util.common.Solver;
import util.grid.IntegerGrid;

import java.util.*;
import java.util.stream.Collectors;

public class GiantSquidSolver extends Solver<Integer> {

    public static final int BINGO_SIZE = 5;
    private final List<Integer> randomNumbers;
    private final List<IntegerGrid> bingoList;

    public GiantSquidSolver(String filename) {
        super(filename);
        this.randomNumbers = parseRandomNumbers();
        this.bingoList = parseBingos();
    }

    private List<Integer> parseRandomNumbers() {
        return Arrays.stream(puzzle.get(0).split(","))
                .map(Integer::parseInt).collect(Collectors.toList());
    }

    private List<IntegerGrid> parseBingos() {
        List<IntegerGrid> bingoArrayList = new ArrayList<>();
        int head = 2;
        while (head <= puzzle.size() - BINGO_SIZE) {
            bingoArrayList.add(new Bingo(puzzle.subList(head, head + BINGO_SIZE), this::tokenizeGridLine));
            head += BINGO_SIZE + 1;
        }
        return bingoArrayList;
    }

    private List<Integer> tokenizeGridLine(String gridLine) {
        List<Integer> strings = new ArrayList<>();
        StringTokenizer stringTokenizer = new StringTokenizer(gridLine);
        while (stringTokenizer.hasMoreTokens()) {
            strings.add(Integer.parseInt(stringTokenizer.nextToken()));
        }
        return strings;
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
            for (IntegerGrid integerGrid : bingoList) {
                Bingo bingo = (Bingo) integerGrid;
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
        bingoList.forEach(IntegerGrid::reset);
        return getResultByLastWinner().orElse(null);
    }

    private Optional<Integer> getResultByLastWinner() {
        Optional<Integer> result = Optional.empty();
        int winBingoCounter = 0;
        Integer lastRandomNumber = randomNumbers.get(0);
        Bingo lastBingo = (Bingo) bingoList.get(0);
        for (int i = 0; i < randomNumbers.size() && winBingoCounter < bingoList.size(); i++) {
            lastRandomNumber = randomNumbers.get(i);
            for (IntegerGrid integerGrid : bingoList) {
                Bingo bingo = (Bingo) integerGrid;
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
