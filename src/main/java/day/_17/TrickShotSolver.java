package day._17;

import util.common.Solver;
import util.coordinate.Coordinate;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

public class TrickShotSolver extends Solver<Integer> {

    private final Area area;

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
        System.out.println(areaBoundaries);
        return new Area(areaBoundaries);
    }

    @Override
    protected Integer solvePartOne() {
        // "https://en.wikipedia.org/wiki/Triangular_number";
        int minX = (int) Math.sqrt(2d * area.getMinX());
        int maxX = area.getMinX() - 1;
        int minY = area.getMaxY() + 1;
        int maxY = Math.abs(area.getMinY());

        return IntStream.range(minX, maxX)
                .mapToObj(i -> IntStream.range(minY, maxY)
                        .mapToObj(j -> new Probe(new Coordinate(0, 0), new Coordinate(i, j))))
                .flatMap(Function.identity())
                .map(this::getHighestPositionOfProbe)
                .filter(height -> height > 0)
                .max(Comparator.naturalOrder())
                .orElseThrow();
    }

    private Integer getHighestPositionOfProbe(Probe probe) {
        int maxHeight = Integer.MIN_VALUE;
        while (!area.isProbeOverArea(probe)) {
            Coordinate position = probe.getPosition();
            Coordinate velocity = probe.getVelocity();
            Coordinate newPosition = position.add(velocity);
            maxHeight = Math.max(maxHeight, newPosition.getY());
            if (area.isProbeInArea(probe)) {
                break;
            }
            probe.setPosition(newPosition);
            int vy = velocity.getY() - 1;
            int vx = 0;
            if (velocity.getX() > 1) {
                vx = velocity.getX() - 1;
            } else if (velocity.getX() < 1) {
                vx = velocity.getX() + 1;
            }
            probe.setVelocity(new Coordinate(vx, vy));
        }
        return maxHeight;
    }

    @Override
    protected Integer solvePartTwo() {
        return null;
    }

}
