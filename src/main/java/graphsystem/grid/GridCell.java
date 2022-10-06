package graphsystem.grid;

import java.util.Objects;

import static graphsystem.grid.GridCellType.EMPTY;

public class GridCell implements Comparable<GridCell> {

    private GridPosition position;
    private GridCellType type;

    public GridCell(int x, int y) {
        this.position = new GridPosition(x, y);
        setType(EMPTY);
    }

    public GridCell(GridCell cell) {
        setAttributes(cell);
    }

    private void setAttributes(GridCell cell) {
        setPosition(cell.getPosition().copy());
        setType(cell.getType());
    }

    public GridCell copy() {
        return new GridCell(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GridCell cell = (GridCell) o;
        return getPosition().equals(cell.getPosition());
    }

    @Override
    public int hashCode() {
        return Objects.hash(position);
    }

    @Override
    public String toString() {
        return "GridCell{" +
                "position=" + position +
                ", type=" + type +
                '}';
    }

    public GridPosition getPosition() {
        return position;
    }

    public void setPosition(GridPosition position) {
        this.position = position;
    }

    public GridCellType getType() {
        return type;
    }

    public void setType(GridCellType type) {
        this.type = type;
    }

    @Override
    public int compareTo(GridCell other) {
        return this.getPosition().compareTo(other.getPosition());
    }

}