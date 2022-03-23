package day4;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Bingo {

    public static int bingoSize = 5;

    private final int[][] board = new int[bingoSize][bingoSize];

    public Bingo(List<String> bingoParts) {
        IntStream.range(0, bingoSize)
                .forEach(i -> {
                    List<Integer> numbers = tokenizeBingoPart(bingoParts.get(i)).stream()
                            .map(Integer::parseInt)
                            .collect(Collectors.toList());
                    IntStream.range(0, bingoSize)
                            .forEach(j -> board[i][j] = numbers.get(j));
                });
    }

    private List<String> tokenizeBingoPart(String bingoPart) {
        List<String> strings = new ArrayList<>();
        StringTokenizer stringTokenizer = new StringTokenizer(bingoPart);
        while (stringTokenizer.hasMoreTokens()) {
            strings.add(stringTokenizer.nextToken());
        }
        return strings;
    }

    public void print() {
        System.out.println(Arrays.deepToString(board));
    }
}