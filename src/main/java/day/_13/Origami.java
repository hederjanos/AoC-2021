package day._13;

import util.coordinate.Coordinate;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Origami {

    private final Set<Coordinate> initialDots;
    private Set<Coordinate> currentDots;

    public Origami(Set<Coordinate> dots) {
        initialDots = dots;
        currentDots = dots.stream()
                .map(Coordinate::new)
                .collect(Collectors.toSet());
    }

    public Set<Coordinate> getCurrentDots() {
        return currentDots;
    }

    public void fold(Fold fold) {
        currentDots = currentDots.stream()
                .map(coordinate -> reflectCoordinate(fold, coordinate))
                .collect(Collectors.toSet());
    }

    private Coordinate reflectCoordinate(Fold fold, Coordinate coordinate) {
        Coordinate newCoordinate;
        if (Fold.Mirror.HORIZONTAL.equals(fold.getMirror())) {
            if (coordinate.getY() <= fold.getValue()) {
                newCoordinate = new Coordinate(coordinate);
            } else {
                newCoordinate = coordinate.subtract(new Coordinate(0, 2 * (coordinate.getY() - fold.getValue())));
            }
        } else {
            if (coordinate.getX() <= fold.getValue()) {
                newCoordinate = new Coordinate(coordinate);
            } else {
                newCoordinate = coordinate.subtract(new Coordinate(2 * (coordinate.getX() - fold.getValue()), 0));
            }
        }
        return newCoordinate;
    }

    public void reset() {
        currentDots = initialDots.stream()
                .map(Coordinate::new)
                .collect(Collectors.toSet());
    }

    @Override
    public String toString() {
        int height = getHeight();
        int width = getWidth();
        return IntStream.range(0, height)
                .mapToObj(i ->
                        IntStream.range(0, width)
                                .mapToObj(j -> new Coordinate(j, i))
                                .map(coordinate -> currentDots.contains(coordinate) ? "#" : ".")
                                .collect(Collectors.joining()))
                .collect(Collectors.joining(System.lineSeparator()));
    }

    private Integer getWidth() {
        return currentDots.stream().map(Coordinate::getX).max(Integer::compareTo).orElse(0) + 1;
    }

    private Integer getHeight() {
        return currentDots.stream().map(Coordinate::getY).max(Integer::compareTo).orElse(0) + 1;
    }

}
