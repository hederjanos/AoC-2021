package day._4;

import util.grid.GridCell;
import util.grid.IntegerGrid;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Bingo extends IntegerGrid {

    private boolean isAlreadyWon;
    private Set<GridCell<Integer>> markedCells = new HashSet<>();

    public Bingo(List<String> gridLines, Function<String, List<Integer>> tokenizer) {
        super(gridLines, tokenizer);
    }

    public void markRandomNumberInBingoIfExists(Integer randomNumber) {
        board.stream()
                .filter(bingoCell -> Objects.equals(bingoCell.getValue(), randomNumber))
                .forEach(bingoCell -> markedCells.add(bingoCell));
    }

    public void clearMarkedCells() {
        markedCells = new HashSet<>();
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
            if (markedCells.contains(board.get(i))) {
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
                .filter(bingoCell -> !markedCells.contains(bingoCell))
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
