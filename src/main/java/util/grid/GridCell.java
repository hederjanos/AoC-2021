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
