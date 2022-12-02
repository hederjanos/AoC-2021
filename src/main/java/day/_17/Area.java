package day._17;

import util.coordinate.Coordinate;

import java.util.List;

public class Area {

    private final int minX;
    private final int maxX;
    private final int minY;
    private final int maxY;

    public Area(List<Integer> boundaries) {
        minX = boundaries.get(0);
        maxX = boundaries.get(1);
        minY = boundaries.get(2);
        maxY = boundaries.get(3);
    }

    public boolean isProbeInArea(Probe probe) {
        Coordinate coordinate = probe.getPosition();
        return coordinate.getX() <= maxX && coordinate.getX() >= minX && coordinate.getY() <= maxY && coordinate.getY() >= minY;
    }

    public boolean isProbeOverArea(Probe probe) {
        Coordinate coordinate = probe.getPosition();
        return coordinate.getX() > maxX || coordinate.getY() < minY;
    }

    public int getMinX() {
        return minX;
    }

    public int getMinY() {
        return minY;
    }

    public int getMaxY() {
        return maxY;
    }

    public int getMaxX() {
        return maxX;
    }

}
