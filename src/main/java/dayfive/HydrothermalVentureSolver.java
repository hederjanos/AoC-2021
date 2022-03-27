package dayfive;

import coordinate.Coordinate;
import util.Solver;

import java.util.*;
import java.util.stream.Collectors;

public class HydrothermalVentureSolver extends Solver<Integer> {

    protected HydrothermalVentureSolver(String filename) {
        super(filename);
    }

    @Override
    protected Integer solvePartOne() {
        List<Coordinate> coordinates = getInLineCoordinates();
        coordinates.sort(Coordinate.getXOrderComparator());
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

    private List<Coordinate> getInLineCoordinates() {
        List<Coordinate> allCoordinates = new ArrayList<>();
        List<List<Coordinate>> listOfCoordinatePairs = parseCoordinates();
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
    protected Integer solvePartTwo() {
        return null;
    }
}
