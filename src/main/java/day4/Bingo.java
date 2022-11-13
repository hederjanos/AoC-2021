package day4;

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
        board.forEach(gridCell -> {
            if (Objects.equals(gridCell.getValue(), randomNumber)) {
                gridCell.setMarked();
            }
        });
    }

    public boolean isWin() {
        boolean isWin = false;
        int[] verticalCounters = new int[width];
        int[] horizontalCounters = new int[height];
        int horizontalCounter = 0;

        for (int i = 0; i < board.size(); i++) {
            if (i % width == 0 && i != 0) {
                horizontalCounter++;
            }
            int verticalCounter = i % height;
            if (board.get(i).isMarked()) {
                horizontalCounters[horizontalCounter]++;
                verticalCounters[verticalCounter]++;
            }
            if (Arrays.stream(verticalCounters)
                        .anyMatch(number -> number == height) ||
                Arrays.stream(horizontalCounters)
                        .anyMatch(number -> number == width)) {
                isWin = true;
                break;
            }
        }
        return isWin;
    }

    public Integer getSumOfUnMarkedNumbers() {
        return board.stream()
                .filter(bingoCell -> !bingoCell.isMarked())
                .map(GridCell::getValue)
                .reduce(0, Integer::sum);
    }

    public boolean isAlreadyWon() {
        return isAlreadyWon;
    }

    public void setAlreadyWon() {
        isAlreadyWon = true;
    }

}
