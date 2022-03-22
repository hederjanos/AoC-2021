package day3;

import util.Solver;

import java.util.Arrays;
import java.util.stream.IntStream;

public class BinaryDiagnosticSolver extends Solver<Integer> {

    public BinaryDiagnosticSolver(String filename) {
        super(filename);
    }

    @Override
    protected Integer solvePartOne() {
        int[] mostCommonBits = getMostCommonBitsByPosition();
        return getConsumption(mostCommonBits);
    }

    @Override
    protected Integer solvePartTwo() {
        return null;
    }

    private int[] getMostCommonBitsByPosition() {
        int[] countArray = new int[puzzle.get(0).length()];
        puzzle.forEach(line -> addBitsFromLineToCountArray(line, countArray));
        return Arrays.stream(countArray).map(sumOfBits -> (puzzle.size() - sumOfBits <= sumOfBits) ? 1 : 0).toArray();
    }

    private void addBitsFromLineToCountArray(String line, int[] countArray) {
        IntStream.range(0, line.length()).forEach(index -> countArray[index] += Character.getNumericValue(line.charAt(index)));
    }

    private int getConsumption(int[] sums) {
        StringBuilder gammaRate = new StringBuilder();
        StringBuilder epsilonRate = new StringBuilder();
        Arrays.stream(sums).forEach(number -> {
            gammaRate.append(number);
            epsilonRate.append((number == 1) ? 0 : 1);
        });
        return Integer.parseInt(gammaRate.toString(), 2) * Integer.parseInt(epsilonRate.toString(), 2);
    }
}
