package util.grid;

import util.coordinate.Coordinate;

public class GridCell<V> {

    private final Coordinate coordinate;
    private V value;
    private boolean marked;

    public GridCell(Coordinate coordinate, V number) {
        this.coordinate = coordinate;
        this.value = number;
    }

    public GridCell(GridCell<V> cell) {
        this.coordinate = cell.getCoordinate().copy();
        this.value = cell.getValue();
        this.marked = cell.isMarked();
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

}
