package day._2;

public enum Direction {

    UP("up"),
    DOWN("down"),
    FORWARD("forward");

    private final String dir;

    Direction(String dir) {
        this.dir = dir;
    }

    public String getDisplayedDirection() {
        return dir;
    }

}
