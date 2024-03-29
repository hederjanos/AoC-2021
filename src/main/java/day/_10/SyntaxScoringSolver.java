package day._10;

import util.common.Solver;

import java.util.*;
import java.util.stream.Collectors;

public class SyntaxScoringSolver extends Solver<Integer> {

    private final List<Character> openers;
    private final List<Character> closers;

    public SyntaxScoringSolver(String filename) {
        super(filename);
        openers = Arrays.stream(ChunkBoundaries.values()).map(ChunkBoundaries::getOpener).collect(Collectors.toList());
        closers = Arrays.stream(ChunkBoundaries.values()).map(ChunkBoundaries::getCloser).collect(Collectors.toList());
    }

    @Override
    protected Integer solvePartOne() {
        return puzzle.stream()
                .map(this::processLine)
                .filter(tuple -> tuple.getFirstElement() != null)
                .mapToInt(tuple -> ChunkBoundaries.values()[tuple.getFirstElement()].getPenalty())
                .sum();
    }

    private CustomTuple processLine(String codeLine) {
        Integer boundaries = null;
        List<Integer> boundariesList = new ArrayList<>();
        boolean isCorrupted = false;
        Deque<Character> stack = new ArrayDeque<>();
        for (int i = 0; i < codeLine.length(); i++) {
            char currentChar = codeLine.charAt(i);
            if (stack.isEmpty() || openers.contains(currentChar)) {
                stack.push(currentChar);
            }
            int indexOfCloser = closers.indexOf(currentChar);
            if (indexOfCloser != -1) {
                Character top = stack.pop();
                int indexOfTop = openers.indexOf(top);
                int indexOfCurrent = closers.indexOf(currentChar);
                if (indexOfTop != indexOfCurrent) {
                    boundaries = indexOfCurrent;
                    isCorrupted = true;
                    break;
                }
            }
        }
        if (!isCorrupted) {
            while (!stack.isEmpty()) {
                boundariesList.add(ChunkBoundaries.get(stack.pop()));
            }
        }
        return new CustomTuple(boundaries, Collections.unmodifiableList(boundariesList));
    }

    @Override
    protected Integer solvePartTwo() {
        List<Long> scores = puzzle.stream()
                .map(this::processLine)
                .filter(tuple -> tuple.getFirstElement() == null)
                .map(tuple -> tuple.getSecondElement().stream()
                        .map(index -> ChunkBoundaries.values()[index].getBonus())
                        .reduce(0L, (subtotal, current) -> {
                            subtotal *= 5;
                            subtotal += current;
                            return subtotal;
                        }))
                .sorted()
                .collect(Collectors.toList());
        return scores.get((scores.size() - 1) / 2).intValue();

    }

}
