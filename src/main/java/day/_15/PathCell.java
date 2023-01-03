package day._15;

import util.coordinate.Coordinate;

import java.util.Objects;

public final class PathCell implements Comparable<PathCell> {

    private final Coordinate coordinate;
    private Integer sumRisk;

    public PathCell(Coordinate coordinate, Integer sumRisk) {
        this.coordinate = coordinate;
        this.sumRisk = sumRisk;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public Integer getSumRisk() {
        return sumRisk;
    }

    void setSumRisk(Integer sumRisk) {
        this.sumRisk = sumRisk;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PathCell)) return false;
        PathCell pathCell = (PathCell) o;
        return Objects.equals(getCoordinate(), pathCell.getCoordinate()) && Objects.equals(getSumRisk(), pathCell.getSumRisk());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCoordinate(), getSumRisk());
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
