package util.grid;

import util.coordinate.Coordinate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.IntStream;

public abstract class IntegerGrid extends Grid<Integer> {

    protected final boolean fourWayDirection;

    protected IntegerGrid(List<String> gridLines, Function<String, List<Integer>> tokenizer) {
        this(gridLines, tokenizer, true);
    }

    protected IntegerGrid(List<String> gridLines, Function<String, List<Integer>> tokenizer, boolean fourWayDirection) {
        super(gridLines);
        width = tokenizer.apply(gridLines.get(0)).size();
        board = new ArrayList<>();
        IntStream.range(0, height)
                .forEach(i -> {
                    List<Integer> numbers = new ArrayList<>(tokenizer.apply(gridLines.get(i)));
                    IntStream.range(0, width)
                            .forEach(j -> board.add(new GridCell<>(new Coordinate(j, i), numbers.get(j))));
                });
        this.fourWayDirection = fourWayDirection;
    }

    public void reset() {
        board.forEach(GridCell::setUnMarked);
    }

    public boolean isCoordinateInBounds(Coordinate coordinate) {
        return coordinate.getX() < width && coordinate.getX() >= 0 && coordinate.getY() < height && coordinate.getY() >= 0;
    }

    public int calculateCellIndex(int x, int y) {
        return x + y * width;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < board.size(); i++) {
            if (i % width == 0 && i != 0) {
                stringBuilder.append("\n");
            }
            stringBuilder.append(board.get(i).getValue());
        }
        return stringBuilder.toString();
    }

}
