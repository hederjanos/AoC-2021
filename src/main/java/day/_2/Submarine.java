package day._2;

public class Submarine {

    private int horizontal;
    private int depth;
    private int aim;

    public int getHorizontal() {
        return horizontal;
    }

    public int getDepth() {
        return depth;
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

    public void aimUp(int value) {
        aim -= value;
    }

    public void aimDown(int value) {
        aim += value;
    }

    public void moveForwardWithAim(int value) {
        horizontal += value;
        depth += aim * value;
    }

}
