package util.grid;

import java.util.List;

public abstract class Grid<V> {

    protected int width;
    protected int height;
    protected List<GridCell<V>> board;

    protected Grid() {
    }

    protected Grid(List<String> gridLines) {
        height = gridLines.size();
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    List<GridCell<V>> getBoard() {
        return board;
    }

}
