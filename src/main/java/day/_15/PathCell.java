package day._15;

import util.coordinate.Coordinate;

public final class PathCell implements Comparable<PathCell> {

    private final Coordinate coordinate;
    private Integer sumRisk;

    public PathCell(Coordinate coordinate, Integer sumRisk) {
        this.coordinate = coordinate.copy();
        this.sumRisk = sumRisk;
    }

    public Coordinate getCoordinate() {
        return coordinate.copy();
    }

    public Integer getSumRisk() {
        return sumRisk;
    }

    public void setSumRisk(Integer sumRisk) {
        this.sumRisk = sumRisk;
    }

    @Override
    public int compareTo(PathCell o) {
       return this.getSumRisk().compareTo(o.getSumRisk());
    }

    @Override
    public String toString() {
        return "Path{" +
               "c: " + coordinate +
               ", r: " + sumRisk +
               '}';
    }
}
