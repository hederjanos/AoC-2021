package day._18;

public class Number {

    private Number parent;
    private Number left;
    private Number right;
    private int value = -1;

    public Number getParent() {
        return parent;
    }

    public void setParent(Number parent) {
        this.parent = parent;
    }

    public Number getLeft() {
        return left;
    }

    public void setLeft(Number left) {
        this.left = left;
    }

    public Number getRight() {
        return right;
    }

    public void setRight(Number right) {
        this.right = right;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public void explode() {
        Number number = explode(this, 0);
        Number parent = number.getParent();
        int left = number.getLeft().getValue();
        int right = number.getRight().getValue();
        Number leftNeighbour = findLeftNeighbourOf(number.getParent());
        Number rightNeighbour = findRightNeighbourOf(number.getParent());
        System.out.println("number: ");
        System.out.println(number);
        System.out.println("parent: ");
        System.out.println(parent);
        System.out.print("leftval: ");
        System.out.println(left);
        System.out.print("rightval: ");
        System.out.println(right);
        System.out.print("leftneighbour: ");
        System.out.println(leftNeighbour);
        System.out.print("rightneighbour: ");
        System.out.println(rightNeighbour);
        Number newNumber = new Number();
        newNumber.setValue(0);
        if (leftNeighbour == null) {
            parent.setLeft(newNumber);
            parent.getRight().setValue(right + rightNeighbour.getValue());
        } else {
            //leftNeighbour.setValue(left + leftNeighbour.getValue());
        }
        if (rightNeighbour == null) {
            parent.setRight(newNumber);
            parent.getLeft().setValue(left + leftNeighbour.getValue());
        } else {
            //parent.getParent().getRight().setValue(right + rightNeighbour.getValue());
        }
    }

    public Number explode(Number root, int level) {
        if (root != null && level == 4) {
            return root;
        }
        if (root.getLeft().getLeft() != null) {
            root = explode(root.getLeft(), ++level);
        } else if (root.getRight().getRight() != null) {
            root = explode(root.getRight(), ++level);
        }
        return root;
    }

    public Number findRightNeighbourOf(Number number) {
        if (number.getRight() != null && number.getRight().getValue() > -1) {
            return number.getRight();
        }
        if (number.getParent() == null) {
            return null;
        }
        return findRightNeighbourOf(number.getParent());
    }

    public Number findLeftNeighbourOf(Number number) {
        if (number.getParent() == null) {
            return null;
        }
        if (number.getLeft() != null && number.getLeft().getValue() > -1) {
            return number.getLeft();
        }
        return findLeftNeighbourOf(number.getParent());
    }

    public int getNumberOfLevels() {
        return getNumberOfLevels(this);
    }

    private int getNumberOfLevels(Number root) {
        if (root == null) {
            return -1;
        } else {
            return 1 + Math.max(getNumberOfLevels(root.getLeft()), getNumberOfLevels(root.getRight()));
        }
    }

    @Override
    public String toString() {
        int height = getNumberOfLevels();
        return toString(this, height);
    }

    private String toString(Number root, int height) {
        StringBuilder sb = new StringBuilder();
        if (root == null) {
            return sb.toString();
        }
        sb.append(toString(root.getLeft(), height - 1));
        sb.append(" ".repeat(Math.max(0, 2 * height)));
        sb.append("----").append((root.getValue() >= 0) ? root.getValue() : "P");
        sb.append("\n");
        sb.append(toString(root.getRight(), height - 1));
        return sb.toString();
    }

}
