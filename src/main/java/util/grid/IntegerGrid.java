package util.grid;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.IntStream;

public abstract class IntegerGrid extends Grid<Integer> {

    protected IntegerGrid(List<String> gridLines, Function<String, List<Integer>> tokenizer) {
        super(gridLines);
        width = tokenizer.apply(gridLines.get(0)).size();
        board = new GridCell[height][width];
        IntStream.range(0, height)
                .forEach(i -> {
                    List<Integer> numbers = new ArrayList<>(tokenizer.apply(gridLines.get(i)));
                    IntStream.range(0, width)
                            .forEach(j -> board[i][j] = new GridCell<>(numbers.get(j)));
                });
    }

}
