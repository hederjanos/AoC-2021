package graphsystem.grid;

import java.util.Objects;

public class GridPosition {

    private int x;
    private int y;

    public GridPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public GridPosition(GridPosition position) {
        setX(position.getX());
        setY(position.getY());
    }

    public GridPosition copy() {
        return new GridPosition(this);
    }

    public int calculateSquareDistance(GridPosition position) {
        return (x - position.getX()) * (x - position.getX()) +
                (y - position.getY()) * (y - position.getY());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GridPosition that = (GridPosition) o;
        return x == that.x && y == that.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "{x=" + x + ", y=" + y + "}";
    }

    public int getX() {
        return x;
    }

    public void setX(int row) {
        this.x = row;
    }

    public int getY() {
        return y;
    }

    public void setY(int col) {
        this.y = col;
    }

}