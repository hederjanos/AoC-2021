package day._18;

import util.common.Solver;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import java.util.stream.Collectors;

public class SnailFishSolver extends Solver<Integer> {

    private List<SnailFishNumber> numbers;

    public SnailFishSolver(String filename) {
        super(filename);
        numbers = puzzle.stream().map(this::parseANumber).collect(Collectors.toList());
        SnailFishNumber number = numbers.get(0);
        System.out.println("_______________NUMBER________________");
        System.out.println(number);
        number.explode();
        System.out.println("_______________NUMBER________________");
        System.out.println(number);
    }

    private SnailFishNumber parseANumber(String line) {
        Deque<SnailFishNumber> stack = new ArrayDeque<>();
        SnailFishNumber root = null;
        for (int i = 0; i < line.length(); i++) {
            if (line.charAt(i) == '[') {
                SnailFishNumber number = new SnailFishNumber();
                if (i == 0) {
                    root = number;
                    stack.push(number);
                    continue;
                } else {
                    number.setParent(stack.peek());
                    if (line.charAt(i - 1) != ',') {
                        number.setLeft(true);
                        stack.peek().setLeft(number);
                    } else {
                        stack.peek().setRight(number);
                    }
                }
                stack.push(number);
            } else if (Character.isDigit(line.charAt(i))) {
                SnailFishNumber number = new SnailFishNumber();
                number.setParent(stack.peek());
                number.setValue(Integer.parseInt(line.substring(i, i + 1)));
                if (line.charAt(i + 1) == ',') {
                    number.setLeft(true);
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
