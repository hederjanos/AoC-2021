package day._17;

import util.common.Solver;
import util.coordinate.Coordinate;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class TrickShotSolver extends Solver<Integer> {

    private final Area area;
    private List<Integer> maxHeightsOfTrajectories;

    public TrickShotSolver(String filename) {
        super(filename);
        area = parseArea();
    }

    private Area parseArea() {
        List<Integer> areaBoundaries = new ArrayList<>();
        Pattern pattern = Pattern.compile("-?[0-9]+");
        Matcher matcher = pattern.matcher(puzzle.get(0));
        while (matcher.find()) {
            areaBoundaries.add(Integer.parseInt(matcher.group()));
        }
        return new Area(areaBoundaries);
    }

    @Override
    protected Integer solvePartOne() {
        maxHeightsOfTrajectories = calculateMaxHeightsOfTrajectories();
        return maxHeightsOfTrajectories.stream().max(Comparator.naturalOrder()).orElseThrow();
    }

    private List<Integer> calculateMaxHeightsOfTrajectories() {
        // "https://en.wikipedia.org/wiki/Triangular_number";
        int minX = (int) Math.sqrt(2d * area.getMinX());
        int maxX = area.getMaxX();
        int minY = area.getMinY();
        int maxY = Math.abs(area.getMinY());

        return IntStream.rangeClosed(minX, maxX)
                .mapToObj(i -> IntStream.rangeClosed(minY, maxY)
                        .mapToObj(j -> new Probe(new Coordinate(0, 0), new Coordinate(i, j))))
                .flatMap(Function.identity())
                .map(this::getHighestPositionOfProbe)
                .filter(height -> height > Integer.MIN_VALUE)
                .collect(Collectors.toList());
    }

    private Integer getHighestPositionOfProbe(Probe probe) {
        int maxHeight = Integer.MIN_VALUE;
        boolean probeInTarget = false;
        while (!area.isProbeOverArea(probe)) {
            probe.fly();
            maxHeight = Math.max(maxHeight, probe.getPosition().getY());
            if (area.isProbeInArea(probe)) {
                probeInTarget = true;
                break;
            }
        }
        return probeInTarget ? maxHeight : Integer.MIN_VALUE;
    }

    @Override
    protected Integer solvePartTwo() {
        return maxHeightsOfTrajectories.size();
    }

}
