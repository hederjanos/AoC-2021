package day._13;

import util.coordinate.Coordinate;

import java.util.HashSet;
import java.util.Set;

public class Origami {

    private final Set<Coordinate> initialDots;
    private Set<Coordinate> currentDots;

    public Origami(Set<Coordinate> dots) {
        this.initialDots = dots;
        this.currentDots = new HashSet<>();
        dots.forEach(coordinate -> currentDots.add(new Coordinate(coordinate)));
    }

    public Set<Coordinate> getCurrentDots() {
        return currentDots;
    }

    public void fold(Fold fold) {
        Set<Coordinate> newDots = new HashSet<>();
        currentDots.forEach(coordinate -> {
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
            newDots.add(newCoordinate);
        });
        currentDots = newDots;
    }

    public void reset() {
        this.currentDots = new HashSet<>();
        initialDots.forEach(coordinate -> currentDots.add(new Coordinate(coordinate)));
    }

    @Override
    public String toString() {
        int maxI = getMaxY();
        int maxJ = getMaxX();
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i <= maxI; i++) {
            for (int j = 0; j <= maxJ; j++) {
                if (currentDots.contains(new Coordinate(j, i))) {
                    stringBuilder.append("#");
                } else {
                    stringBuilder.append(".");
                }
            }
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }

    private Integer getMaxX() {
        return currentDots.stream().map(Coordinate::getX).max(Integer::compareTo).orElse(null);
    }

    private Integer getMaxY() {
        return currentDots.stream().map(Coordinate::getY).max(Integer::compareTo).orElse(null);
    }

}
