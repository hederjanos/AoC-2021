package dayten;

import util.common.Solver;

import java.util.*;
import java.util.stream.Collectors;

public class SyntaxScoringSolver extends Solver<Integer> {

    private final List<Character> openers;
    private final List<Character> closers;

    public SyntaxScoringSolver(String filename) {
        super(filename);
        openers = Arrays.stream(ChunkBoundaries.values())
                .map(ChunkBoundaries::getOpener)
                .collect(Collectors.toList());
        closers = Arrays.stream(ChunkBoundaries.values())
                .map(ChunkBoundaries::getCloser)
                .collect(Collectors.toList());
    }

    @Override
    protected Integer solvePartOne() {
        return puzzle.stream()
                .map(this::processLine)
                .filter(tuple -> tuple.getFirstElement() != null)
                .map(tuple -> tuple.getFirstElement().getPenalty())
                .reduce(0, Integer::sum);
    }

    private CustomTuple processLine(String codeLine) {
        ChunkBoundaries boundaries = null;
        List<ChunkBoundaries> boundariesList = new ArrayList<>();
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
                    boundaries = ChunkBoundaries.values()[indexOfCloser];
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
        return new CustomTuple(boundaries, boundariesList);
    }

    @Override
    protected Integer solvePartTwo() {
        List<Long> scores = puzzle.stream()
                .map(this::processLine)
                .filter(tuple -> tuple.getFirstElement() == null)
                .map(tuple -> tuple.getSecondElement().stream()
                        .map(ChunkBoundaries::getBonus)
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
