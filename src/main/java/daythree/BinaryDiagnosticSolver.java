package daythree;

import util.common.Solver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class BinaryDiagnosticSolver extends Solver<Integer> {

    public BinaryDiagnosticSolver(String filename) {
        super(filename);
    }

    @Override
    protected Integer solvePartOne() {
        int[] mostCommonBits = getMostCommonBitsByPosition(puzzle);
        return getResult(getRatingsForConsumption(mostCommonBits));
    }

    private int[] getMostCommonBitsByPosition(List<String> puzzle) {
        int[] countArray = new int[puzzle.get(0).length()];
        puzzle.forEach(line -> addBitsFromLineToCountArray(line, countArray));
        return Arrays.stream(countArray).map(sumOfBits -> (puzzle.size() - sumOfBits <= sumOfBits) ? 1 : 0).toArray();
    }

    private void addBitsFromLineToCountArray(String line, int[] countArray) {
        IntStream.range(0, line.length()).forEach(index -> countArray[index] += Character.getNumericValue(line.charAt(index)));
    }

    private List<String> getRatingsForConsumption(int[] mostCommonBits) {
        List<String> ratings = new ArrayList<>();
        StringBuilder gammaRate = new StringBuilder();
        StringBuilder epsilonRate = new StringBuilder();
        Arrays.stream(mostCommonBits).forEach(number -> {
            gammaRate.append(number);
            epsilonRate.append((number == 1) ? 0 : 1);
        });
        ratings.add(gammaRate.toString());
        ratings.add(epsilonRate.toString());
        return ratings;
    }

    private int getResult(List<String> ratings) {
        return ratings.stream().map(string -> Integer.parseInt(string, 2)).reduce(1, (a, b) -> a * b);
    }

    @Override
    protected Integer solvePartTwo() {
        return getResult(getRatingsForLifeSupportRating());
    }

    private List<String> getRatingsForLifeSupportRating() {
        List<String> ratings = new ArrayList<>();
        String oxygenGeneratorRating = getRating(true);
        String carbonDioxideScrubberRating = getRating(false);
        ratings.add(oxygenGeneratorRating);
        ratings.add(carbonDioxideScrubberRating);
        return ratings;
    }

    private String getRating(boolean bitCriteria) {
        List<String> diagnosticReport = copyPuzzle();
        int index = 0;
        while (diagnosticReport.size() > 1) {
            int[] mostCommonBits = getMostCommonBitsByPosition(diagnosticReport);
            int finalIndex = index;
            diagnosticReport = diagnosticReport.stream()
                    .filter(line -> isRemainingLine(bitCriteria, mostCommonBits, finalIndex, line))
                    .collect(Collectors.toList());
            index++;
        }
        return diagnosticReport.stream().findFirst().orElse(null);
    }

    private boolean isRemainingLine(boolean bitCriteria, int[] mostCommonBits, int finalIndex, String line) {
        int filterOption;
        if (bitCriteria) {
            filterOption = mostCommonBits[finalIndex];
        } else {
            if (mostCommonBits[finalIndex] == 1) {
                filterOption = 0;
            } else {
                filterOption = 1;
            }
        }
        return Character.getNumericValue(line.charAt(finalIndex)) == filterOption;
    }

    private List<String> copyPuzzle() {
        return new ArrayList<>(puzzle);
    }

}
