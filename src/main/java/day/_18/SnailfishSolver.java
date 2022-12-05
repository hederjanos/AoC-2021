package day._18;

import util.common.Solver;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import java.util.stream.Collectors;

public class SnailfishSolver extends Solver<Integer> {

    private List<Number> numbers;

    public SnailfishSolver(String filename) {
        super(filename);
        numbers = puzzle.stream()
                .map(this::parseANumber)
                .collect(Collectors.toList());
    }

    private Number parseANumber(String line) {
        Deque<Number> stack = new ArrayDeque<>();
        Number root = null;
        for (int i = 0; i < line.length(); i++) {
            if (line.charAt(i) == '[') {
                Number number = new Number();
                if (i == 0) {
                    root = number;
                    stack.push(number);
                    continue;
                } else {
                    if (line.charAt(i - 1) != ',') {
                        stack.peek().setLeft(number);
                    } else {
                        stack.peek().setRight(number);
                    }
                }
                stack.push(number);
            } else if (Character.isDigit(line.charAt(i))) {
                Number number = new Number();
                number.setValue(Integer.parseInt(line.substring(i, i + 1)));
                if (line.charAt(i + 1) == ',') {
                    stack.peek().setLeft(number);
                } else {
                    stack.peek().setRight(number);
                }
            } else if (line.charAt(i) == ']') {
                root = stack.pop();
            }
        }
        return root;
    }

    @Override
    protected Integer solvePartOne() {
        return null;
    }

    @Override
    protected Integer solvePartTwo() {
        return null;
    }

}
