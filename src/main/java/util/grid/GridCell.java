package util.grid;

import util.coordinate.Coordinate;

public class GridCell<V> {

    private final Coordinate coordinate;
    private V value;
    private boolean marked;

    public GridCell(Coordinate coordinate, V value) {
        this.coordinate = coordinate;
        this.value = value;
    }

    public GridCell(GridCell<V> cell) {
        coordinate = cell.getCoordinate().copy();
        value = cell.getValue();
        marked = cell.isMarked();
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

    public boolean isMarked() {
        return marked;
    }

    public void setMarked() {
        this.marked = true;
    }

    public void setUnMarked() {
        this.marked = false;
    }

    @Override
    public String toString() {
        return "Cell{" +
               "c: " + coordinate +
               ", v: " + value +
               ", m: " + marked +
               '}';
    }
}
