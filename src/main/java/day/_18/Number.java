package day._18;

public class Number {

    private Number left;
    private Number right;
    private int value = -1;

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

    public int getNumberOfLevels() {
        return getNumberOfLevels(this);
    }

    private int getNumberOfLevels(Number number) {
        if (number == null) {
            return -1;
        } else {
            return 1 + Math.max(getNumberOfLevels(number.getLeft()), getNumberOfLevels(number.getRight()));
        }
    }

    @Override
    public String toString() {
        int height = getNumberOfLevels();
        return toString(this, height);
    }

    private String toString(Number number, int height) {
        StringBuilder sb = new StringBuilder();
        if (number == null) {
            return sb.toString();
        }
        sb.append(toString(number.getLeft(), height - 1));
        sb.append(" ".repeat(Math.max(0, 2 * height)));
        sb.append("----").append((number.getValue() >= 0) ? number.getValue() : "P");
        sb.append("\n");
        sb.append(toString(number.getRight(), height - 1));
        return sb.toString();
    }

}
