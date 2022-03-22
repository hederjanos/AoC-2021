package day2;

public class Position {

    private int horizontal;
    private int depth;

    public Position(int x, int y) {
        this.horizontal = x;
        this.depth = y;
    }

    public int getHorizontal() {
        return horizontal;
    }

    public void setHorizontal(int horizontal) {
        this.horizontal = horizontal;
    }

    public int getDepth() {
        return depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public void sink(int value) {
        depth += value;
    }

    public void rise(int value) {
        depth -= value;
    }

    public void moveForward(int value) {
        horizontal += value;
    }
}
