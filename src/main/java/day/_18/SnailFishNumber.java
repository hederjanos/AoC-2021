package day._18;

import java.util.ArrayDeque;
import java.util.Deque;

public class SnailFishNumber {

    private SnailFishNumber parent;
    private SnailFishNumber left;
    private SnailFishNumber right;
    private int value = -1;
    private boolean isLeft;

    public static SnailFishNumber parseANumber(String numberString) {
        Deque<SnailFishNumber> stack = new ArrayDeque<>();
        SnailFishNumber root = null;
        for (int i = 0; i < numberString.length(); i++) {
            if (numberString.charAt(i) == '[') {
                SnailFishNumber number = new SnailFishNumber();
                if (i == 0) {
                    root = number;
                    stack.push(number);
                    continue;
                } else {
                    number.setParent(stack.peek());
                    if (numberString.charAt(i - 1) != ',') {
                        number.setLeft(true);
                        stack.peek().setLeft(number);
                    } else {
                        stack.peek().setRight(number);
                    }
                }
                stack.push(number);
            } else if (Character.isDigit(numberString.charAt(i))) {
                SnailFishNumber number = new SnailFishNumber();
                number.setParent(stack.peek());
                number.setValue(Integer.parseInt(numberString.substring(i, i + 1)));
                if (numberString.charAt(i + 1) == ',') {
                    number.setLeft(true);
                    stack.peek().setLeft(number);
                } else {
                    stack.peek().setRight(number);
                }
            } else if (numberString.charAt(i) == ']') {
                root = stack.pop();
            }
        }
        return root;
    }

    public SnailFishNumber add(SnailFishNumber number) {
        SnailFishNumber newNumber = new SnailFishNumber();
        this.setParent(newNumber);
        number.setParent(newNumber);
        newNumber.setLeft(this);
        this.setLeft(true);
        newNumber.setRight(number);
        return newNumber;
    }

    public void explode(boolean withPrint) {
        SnailFishNumber explodingNumber = getExploding();
        if (explodingNumber != null) {
            SnailFishNumber parentNumber = explodingNumber.getParent();
            int leftValue = explodingNumber.getLeft().getValue();
            int rightValue = explodingNumber.getRight().getValue();
            SnailFishNumber leftNeighbour = explodingNumber.getLeftNeighbour();
            SnailFishNumber rightNeighbour = explodingNumber.getRightNeighbour();

            if (withPrint) {
                System.out.println("exploding number: ");
                System.out.println(explodingNumber);
                System.out.println("parent: ");
                System.out.println(parentNumber);
                System.out.print("leftval: ");
                System.out.println(leftValue);
                System.out.print("rightval: ");
                System.out.println(rightValue);
                System.out.print("leftneighbour: ");
                System.out.println(leftNeighbour);
                System.out.print("rightneighbour: ");
                System.out.println(rightNeighbour);
            }

            SnailFishNumber newNumber = new SnailFishNumber();
            newNumber.setValue(0);
            newNumber.setParent(parentNumber);
            if (leftNeighbour != null && rightNeighbour != null) {
                if (explodingNumber.isLeft()) {
                    newNumber.setLeft(true);
                    parentNumber.setLeft(newNumber);
                } else {
                    parentNumber.setRight(newNumber);
                }
                leftNeighbour.setValue(leftNeighbour.getValue() + leftValue);
                rightNeighbour.setValue(rightNeighbour.getValue() + rightValue);
            } else if (leftNeighbour == null) {
                parentNumber.setLeft(newNumber);
                newNumber.setLeft(true);
                rightNeighbour.setValue(rightNeighbour.getValue() + rightValue);
            } else {
                parentNumber.setRight(newNumber);
                leftNeighbour.setValue(leftNeighbour.getValue() + leftValue);
            }
        }
    }

    private SnailFishNumber getExploding() {
        return getExploding(this, -1);
    }

    private SnailFishNumber getExploding(SnailFishNumber root, int level) {
        if (root == null) {
            return null;
        }
        if (level == 4) {
            if (root.getLeft() == null && root.getRight() == null) {
                return root.getParent();
            }
            return root;
        }
        SnailFishNumber snailFishNumber = getExploding(root.getLeft(), ++level);
        --level;
        if (snailFishNumber != null) {
            return snailFishNumber;
        }
        snailFishNumber = getExploding(root.getRight(), ++level);
        return snailFishNumber;
    }

    private SnailFishNumber getLeftNeighbour() {
        if (parent == null) {
            return null;
        } else if (!isLeft) {
            SnailFishNumber number = parent.getLeft();
            while ((number.getLeft() != null && number.getRight() != null)) {
                number = number.getRight();
            }
            return number;
        } else {
            return parent.getLeftNeighbour();
        }
    }

    private SnailFishNumber getRightNeighbour() {
        if (parent == null) {
            return null;
        } else if (isLeft) {
            SnailFishNumber number = parent.getRight();
            while (number.getLeft() != null && number.getRight() != null) {
                number = number.getLeft();
            }
            return number;
        } else {
            return parent.getRightNeighbour();
        }
    }

    public int getDepth() {
        return getDepth(this);
    }

    private int getDepth(SnailFishNumber root) {
        if (root == null) {
            return -1;
        } else {
            return 1 + Math.max(getDepth(root.getLeft()), getDepth(root.getRight()));
        }
    }

    public SnailFishNumber getParent() {
        return parent;
    }

    public void setParent(SnailFishNumber parent) {
        this.parent = parent;
    }

    public SnailFishNumber getLeft() {
        return left;
    }

    public void setLeft(SnailFishNumber left) {
        this.left = left;
    }

    public SnailFishNumber getRight() {
        return right;
    }

    public void setRight(SnailFishNumber right) {
        this.right = right;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public boolean isLeft() {
        return isLeft;
    }

    public void setLeft(boolean left) {
        isLeft = left;
    }

    @Override
    public String toString() {
        return toString(this, getDepth());
    }

    private String toString(SnailFishNumber root, int depth) {
        StringBuilder sb = new StringBuilder();
        if (root == null) {
            return sb.toString();
        }
        sb.append(toString(root.getLeft(), depth - 1));
        sb.append(" ".repeat(Math.max(0, 2 * depth)));
        sb.append("----").append((root.getValue() >= 0) ? root.getValue() : "P");
        sb.append("\n");
        sb.append(toString(root.getRight(), depth - 1));
        return sb.toString();
    }

}
