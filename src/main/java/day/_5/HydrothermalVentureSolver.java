package day._5;

import util.coordinate.Coordinate;
import util.common.Solver;

import java.util.*;
import java.util.stream.Collectors;

public class HydrothermalVentureSolver extends Solver<Integer> {

    private final List<List<Coordinate>> listOfCoordinatePairs;

    protected HydrothermalVentureSolver(String filename) {
        super(filename);
        this.listOfCoordinatePairs = parseCoordinates();
    }

    private List<List<Coordinate>> parseCoordinates() {
        return puzzle.stream()
                .map(line -> {
                    String[] lineParts = line.split(" -> ");
                    return Arrays.stream(lineParts)
                            .filter(linePart -> Character.isDigit(linePart.charAt(0)))
                            .map(Coordinate::new)
                            .collect(Collectors.toList());
                }).collect(Collectors.toList());
    }

    @Override
    protected Integer solvePartOne() {
        List<Coordinate> coordinates = getCoordinatesInHorizontalOrVerticalLines();
        coordinates.sort(Coordinate.getXOrderComparator());
        return getNumberOfOverlappingCoordinates(coordinates);
    }

    private List<Coordinate> getCoordinatesInHorizontalOrVerticalLines() {
        List<Coordinate> allCoordinates = new ArrayList<>();
        listOfCoordinatePairs.stream()
                .filter(lines -> {
                    Coordinate start = lines.get(0);
                    Coordinate end = lines.get(1);
                    return start.isInHorizontalOrVerticalLineWith(end);
                })
                .forEach(lines -> {
                    Coordinate start = lines.get(0);
                    Coordinate end = lines.get(1);
                    allCoordinates.addAll(start.getCoordinatesInLineBetweenCoordinates(end));
                });
        return allCoordinates;
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
        List<Coordinate> allCoordinates = new ArrayList<>();
        listOfCoordinatePairs
                .forEach(lines -> {
                    Coordinate start = lines.get(0);
                    Coordinate end = lines.get(1);
                    allCoordinates.addAll(start.getCoordinatesInLineBetweenCoordinates(end));
                });
        return allCoordinates;
    }

}
