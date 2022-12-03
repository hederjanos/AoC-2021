package util.grid;

import util.coordinate.Coordinate;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public abstract class IntegerGrid extends Grid<Integer> {

    protected boolean fourWayDirection;

    protected IntegerGrid() {
    }

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

    protected IntegerGrid(IntegerGrid grid) {
        super();
        fourWayDirection = grid.isFourWayDirection();
        width = grid.getWidth();
        height = grid.getHeight();
        board = new ArrayList<>();
        grid.getBoard().forEach(gridCell -> board.add(gridCell.copy()));
    }

    public static Function<String, List<Integer>> convertContiguousIntegersToList() {
        return s -> s.chars()
                .mapToObj(c -> (char) c)
                .map(Character::getNumericValue)
                .collect(Collectors.toList());
    }

    public boolean isFourWayDirection() {
        return fourWayDirection;
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
