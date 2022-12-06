package day._18;

public class SnailFishNumber {

    private SnailFishNumber parent;
    private SnailFishNumber left;
    private SnailFishNumber right;
    private int value = -1;
    private boolean isLeft;

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

    public SnailFishNumber canExplode(SnailFishNumber root, int level) {
        if (root != null && level == 4) {
            return root;
        }
        if (root.getLeft().getLeft() != null) {
            root = canExplode(root.getLeft(), ++level);
        } else if (root.getRight().getRight() != null) {
            root = canExplode(root.getRight(), ++level);
        }
        return root;
    }

    public void explode() {
        SnailFishNumber number = canExplode(this, 0);
        if (number != null) {
            SnailFishNumber parentNumber = number.getParent();
            int leftValue = number.getLeft().getValue();
            int rightValue = number.getRight().getValue();
            SnailFishNumber leftNeighbour = number.getLeftNeighbour();
            SnailFishNumber rightNeighbour = number.getRightNeighbour();
            SnailFishNumber newNumber = new SnailFishNumber();
            newNumber.setValue(0);
            if (leftNeighbour != null && rightNeighbour != null) {
                if (number.isLeft()) {
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
