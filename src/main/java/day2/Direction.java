package day2;

public enum Direction {
    UP("up"),
    DOWN("down"),
    FORWARD("forward");

    private final String direction;

    Direction(String direction) {
        this.direction = direction;
    }

    public String getDisplayedDirection() {
        return direction;
    }
}
