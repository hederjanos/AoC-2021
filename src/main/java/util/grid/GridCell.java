package util.grid;

import util.coordinate.Coordinate;

import java.util.Objects;

public class GridCell<V> {

    private final Coordinate coordinate;
    private V value;

    public GridCell(Coordinate coordinate, V value) {
        this.coordinate = coordinate;
        this.value = value;
    }

    public GridCell(GridCell<V> cell) {
        coordinate = cell.getCoordinate().copy();
        value = cell.getValue();
    }

    public GridCell<V> copy() {
        return new GridCell<>(this);
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public V getValue() {
        return value;
    }

    public void setValue(V value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GridCell)) return false;
        GridCell<?> gridCell = (GridCell<?>) o;
        return Objects.equals(getCoordinate(), gridCell.getCoordinate()) && Objects.equals(getValue(), gridCell.getValue());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCoordinate(), getValue());
    }

    @Override
    public String toString() {
        return "Cell{" +
               "c: " + coordinate +
               ", v: " + value +
               '}';
    }

}
