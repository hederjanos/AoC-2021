package util.grid;

public class GridCell<V> {

    private final V value;
    private boolean marked;

    public GridCell(V number) {
        this.value = number;
    }

    public V getValue() {
        return value;
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
