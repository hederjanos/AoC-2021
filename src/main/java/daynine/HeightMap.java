package daynine;

import dayfour.GridCell;
import dayfour.IntegerGrid;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

public class HeightMap extends IntegerGrid {

    protected HeightMap(List<String> gridLines, Function<String, List<Integer>> tokenizer) {
        super(gridLines, tokenizer);
    }

    public void printHeightMap() {
        Arrays.stream(board).forEach(gridCells -> {
            Arrays.stream(gridCells)
                    .map(GridCell::getValue)
                    .forEach(System.out::print);
            System.out.println();
        });
    }

}
