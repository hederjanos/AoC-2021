package day._13;

import util.coordinate.Coordinate;

import java.util.HashSet;
import java.util.Set;

public class Origami {

    private Set<Coordinate> dots;

    public Origami(Set<Coordinate> dots) {
        this.dots = dots;
    }

    public Set<Coordinate> getDots() {
        return dots;
    }

    public void fold(Fold fold) {
        Set<Coordinate> newDots = new HashSet<>();
        dots.forEach(coordinate -> {
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
        dots = newDots;
    }
}
