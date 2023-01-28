package day._5;

import util.coordinate.Coordinate;
import util.common.Solver;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class HydrothermalVentureSolver extends Solver<Integer> {

    private final List<List<Coordinate>> listOfCoordinatePairs;

    protected HydrothermalVentureSolver(String filename) {
        super(filename);
        this.listOfCoordinatePairs = parseCoordinates();
    }

    private List<List<Coordinate>> parseCoordinates() {
        return puzzle.stream()
                .map(this::parseCoordinatesInLine).collect(Collectors.toList());
    }

    private List<Coordinate> parseCoordinatesInLine(String line) {
        String[] lineParts = line.split(" -> ");
        return Arrays.stream(lineParts)
                .filter(linePart -> Character.isDigit(linePart.charAt(0)))
                .map(Coordinate::new)
                .collect(Collectors.toList());
    }

    @Override
    protected Integer solvePartOne() {
        List<Coordinate> coordinates = getCoordinatesInHorizontalOrVerticalLines();
        coordinates.sort(Coordinate.getXOrderComparator());
        return getNumberOfOverlappingCoordinates(coordinates);
    }

    private List<Coordinate> getCoordinatesInHorizontalOrVerticalLines() {
        return listOfCoordinatePairs.stream()
                .filter(this::testIfCoordinatesInLine)
                .flatMap(this::getCoordinatesInLineStream)
                .collect(Collectors.toList());
    }

    private boolean testIfCoordinatesInLine(List<Coordinate> coordinatePair) {
        Coordinate start = coordinatePair.get(0);
        Coordinate end = coordinatePair.get(1);
        return start.isInHorizontalOrVerticalLineWith(end);
    }

    private Stream<Coordinate> getCoordinatesInLineStream(List<Coordinate> line) {
        Coordinate start = line.get(0);
        Coordinate end = line.get(1);
        return start.getCoordinatesInLineBetweenWith(end).stream();
    }

    private int getNumberOfOverlappingCoordinates(List<Coordinate> coordinates) {
        int numberOfOverlappingCoordinates = 0;
        for (int i = 0; i < coordinates.size() - 1; i++) {
            boolean isOverlapping = nextCoordinateIsOverlapping(coordinates, i);
            if (isOverlapping) {
                numberOfOverlappingCoordinates++;
                while (i + 1 < coordinates.size() && nextCoordinateIsOverlapping(coordinates, i)) {
                    i++;
                }
            }
        }
        return numberOfOverlappingCoordinates;
    }

    private boolean nextCoordinateIsOverlapping(List<Coordinate> coordinates, int i) {
        return coordinates.get(i).equals(coordinates.get(i + 1));
    }

    @Override
    protected Integer solvePartTwo() {
        List<Coordinate> coordinates = getCoordinatesInAllLines();
        coordinates.sort(Coordinate.getXOrderComparator());
        return getNumberOfOverlappingCoordinates(coordinates);
    }

    private List<Coordinate> getCoordinatesInAllLines() {
        return listOfCoordinatePairs.stream()
                .flatMap(this::getCoordinatesInLineStream)
                .collect(Collectors.toList());
    }

}
