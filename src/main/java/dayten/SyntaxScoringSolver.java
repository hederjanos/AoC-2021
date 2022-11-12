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
                .map(this::getFirstIllegalCharacter)
                .filter(Objects::nonNull)
                .map(ChunkBoundaries::getPenalty)
                .reduce(0, Integer::sum);
    }

    private ChunkBoundaries getFirstIllegalCharacter(String codeLine) {
        ChunkBoundaries boundaries = null;
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
                    break;
                }
            }
        }
        return boundaries;
    }

    @Override
    protected Integer solvePartTwo() {
        return null;
    }

}
