package dayfour;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Bingo {

    public static final int BINGO_SIZE = 5;

    private final BingoCell[][] board = new BingoCell[BINGO_SIZE][BINGO_SIZE];
    private boolean isAlreadyWon;

    public Bingo(List<String> bingoParts) {
        IntStream.range(0, BINGO_SIZE)
                .forEach(i -> {
                    List<Integer> numbers = tokenizeBingoPart(bingoParts.get(i)).stream()
                            .map(Integer::parseInt)
                            .collect(Collectors.toList());
                    IntStream.range(0, BINGO_SIZE)
                            .forEach(j -> board[i][j] = new BingoCell(numbers.get(j)));
                });
    }

    private List<String> tokenizeBingoPart(String bingoPart) {
        List<String> strings = new ArrayList<>();
        StringTokenizer stringTokenizer = new StringTokenizer(bingoPart);
        while (stringTokenizer.hasMoreTokens()) {
            strings.add(stringTokenizer.nextToken());
        }
        return strings;
    }

    public void markRandomNumberInBingoIfExists(Integer randomNumber) {
        Arrays.stream(board)
                .forEach(bingoCells -> Arrays.stream(bingoCells)
                        .filter(bingoCell -> Objects.equals(bingoCell.getNumber(), randomNumber))
                        .forEach(BingoCell::setMarked));
    }

    public boolean isWin() {
        boolean isWin = false;
        int[] verticalCounter = new int[Bingo.BINGO_SIZE];
        for (BingoCell[] bingoCells : board) {
            int horizontalCounter = 0;
            for (int i = 0; i < bingoCells.length; i++) {
                if (bingoCells[i].isMarked()) {
                    horizontalCounter++;
                    verticalCounter[i]++;
                }
            }
            if (horizontalCounter == Bingo.BINGO_SIZE) {
                isWin = true;
                break;
            }
        }
        if (!isWin && Arrays.stream(verticalCounter)
                .anyMatch(number -> number == Bingo.BINGO_SIZE)) {
            isWin = true;
        }
        return isWin;
    }

    public Integer getSumOfUnMarkedNumbers() {
        return Arrays.stream(board)
                .flatMap(bingoCells -> Arrays.stream(bingoCells)
                        .filter(bingoCell -> !bingoCell.isMarked())
                        .map(BingoCell::getNumber))
                .reduce(0, Integer::sum);
    }

    public void resetBingo() {
        Arrays.stream(board)
                .forEach(bingoCells -> Arrays.stream(bingoCells)
                        .forEach(BingoCell::setUnMarked));
    }

    public void printBingo() {
        Arrays.stream(board).forEach(bingoCells -> {
            Arrays.stream(bingoCells)
                    .map(bingoCell -> bingoCell.getNumber() + (bingoCell.isMarked() ? "X" : "O") + "\t")
                    .forEach(System.out::print);
            System.out.println();
        });
    }

    public boolean isAlreadyWon() {
        return isAlreadyWon;
    }

    public void setAlreadyWon() {
        isAlreadyWon = true;
    }
}
