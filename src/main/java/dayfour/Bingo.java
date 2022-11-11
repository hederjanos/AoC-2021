package dayfour;

import util.grid.GridCell;
import util.grid.IntegerGrid;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

public class Bingo extends IntegerGrid {

    private boolean isAlreadyWon;

    public Bingo(List<String> gridLines, Function<String, List<Integer>> tokenizer) {
        super(gridLines, tokenizer);
    }

    public void markRandomNumberInBingoIfExists(Integer randomNumber) {
        Arrays.stream(board)
                .forEach(bingoCells -> Arrays.stream(bingoCells)
                        .filter(bingoCell -> Objects.equals(bingoCell.getValue(), randomNumber))
                        .forEach(GridCell::setMarked));
    }

    public boolean isWin() {
        boolean isWin = false;
        int[] verticalCounter = new int[height];
        for (GridCell<Integer>[] bingoCells : board) {
            int horizontalCounter = 0;
            for (int i = 0; i < bingoCells.length; i++) {
                if (bingoCells[i].isMarked()) {
                    horizontalCounter++;
                    verticalCounter[i]++;
                }
            }
            if (horizontalCounter == width) {
                isWin = true;
                break;
            }
        }
        if (!isWin && Arrays.stream(verticalCounter)
                .anyMatch(number -> number == height)) {
            isWin = true;
        }
        return isWin;
    }

    public Integer getSumOfUnMarkedNumbers() {
        return Arrays.stream(board)
                .flatMap(bingoCells -> Arrays.stream(bingoCells)
                        .filter(bingoCell -> !bingoCell.isMarked())
                        .map(GridCell::getValue))
                .reduce(0, Integer::sum);
    }

    public void resetBingo() {
        Arrays.stream(board)
                .forEach(bingoCells -> Arrays.stream(bingoCells)
                        .forEach(GridCell::setUnMarked));
    }

    public void printBingo() {
        Arrays.stream(board).forEach(bingoCells -> {
            Arrays.stream(bingoCells)
                    .map(bingoCell -> bingoCell.getValue() + (bingoCell.isMarked() ? "X" : "O") + "\t")
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
