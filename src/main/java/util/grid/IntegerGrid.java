package util.grid;

import util.coordinate.Coordinate;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.IntStream;

public abstract class IntegerGrid extends Grid<Integer> {

    protected IntegerGrid(List<String> gridLines, Function<String, List<Integer>> tokenizer) {
        super(gridLines);
        width = tokenizer.apply(gridLines.get(0)).size();
        board = new ArrayList<>();
        IntStream.range(0, height)
                .forEach(i -> {
                    List<Integer> numbers = new ArrayList<>(tokenizer.apply(gridLines.get(i)));
                    IntStream.range(0, width)
                            .forEach(j -> board.add(new GridCell<>(new Coordinate(j, i), numbers.get(j))));
                });
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

}
